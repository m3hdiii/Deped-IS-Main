package com.deped.service.user;


import com.deped.config.SharedConfigData;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.ResponseStatus;
import com.deped.model.account.User;
import com.deped.model.config.server.ServerEnumKey;
import com.deped.model.security.PasswordResetToken;
import com.deped.model.security.TokenState;
import com.deped.repository.user.LoginMethod;
import com.deped.repository.user.UserRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import com.deped.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<User> create(User entity) {
        User savedEntity = userRepository.create(entity);
        ResponseEntity<User> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(User entity) {
        Boolean isUpdated = userRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, User.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<User>> fetchAll() {
        List<User> supplies = userRepository.fetchAll();
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(supplies, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<User>> fetchByRange(Range range) {
        List<User> supplies = userRepository.fetchByRange(range);
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(supplies, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<User> fetchById(Object id) {
        User user = userRepository.fetchById(id);
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(User... entities) {
        Boolean isRemoved = userRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, User.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(User... entities) {
        return null;
    }

    @Override
    public boolean isUserAlreadyExist(User user) {
        return false;
    }

    @Override
    public User loginUser(User user) {
        //TODO check and distinguish user login method using regex
        return userRepository.loginUser(user, LoginMethod.USERNAME);
    }

    @Override
    public User fetchByUsername(String username) {
        return userRepository.fetchByUsername(username);
    }

    @Override
    public User fetchByEmail(String email) {
        return userRepository.fetchByEmail(email);
    }

    @Override
    public ResponseEntity<Response> createPasswordResetTokenForUser(User user, String context) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token, new Date(), TokenState.AVAILABLE);

        PasswordResetToken persistedPasswordResetToken = userRepository.createPasswordResetTokenForUser(passwordResetToken);

        if (persistedPasswordResetToken == null) {
            Response response = ServiceUtils.makeResponse(false, Operation.CREATE, PasswordResetToken.class);
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }

        String message = constructMessage(context, passwordResetToken);
        boolean isSent = EmailService.sendEmail(user.getEmailAddress(), "Deped-IS Password Change", message);

        if (isSent) {
            Response response = ServiceUtils.makeResponse(true, Operation.CREATE, PasswordResetToken.class);
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        } else {
            userRepository.removePasswordResetToken(persistedPasswordResetToken);
            Response response = ServiceUtils.makeResponse(false, Operation.CREATE, PasswordResetToken.class);
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity<Response> changePasswordByToken(Long userId, String token, String newPassword) {
        Map<ServerEnumKey, String> configMap = SharedConfigData.getAppConfigs(false);
        int period = Integer.parseInt(configMap.get(ServerEnumKey.PASSWORD_RESET_TOKEN_PERIOD));
        boolean isUpdated = userRepository.changePasswordByToken(userId, token, newPassword, period);

        if (!isUpdated) {
            Response response = new Response(ResponseStatus.FAILED, "Something went wrong. Contact with your administrator");
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        } else {
            Response response = new Response(ResponseStatus.SUCCESSFUL, "Your password updated successfully");
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity<Response> checkToken(Long userId, String token) {
        Map<ServerEnumKey, String> configMap = SharedConfigData.getAppConfigs(false);
        int period = Integer.parseInt(configMap.get(ServerEnumKey.PASSWORD_RESET_TOKEN_PERIOD));
        PasswordResetToken passwordResetToken = userRepository.findByToken(token);

        if (passwordResetToken == null) {
            Response response = new Response(ResponseStatus.FAILED, "No information found");
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }

        if (passwordResetToken.getTokenState() == TokenState.USED) {
            Response response = new Response(ResponseStatus.FAILED, "Reset password url has been used once already");
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }

        if (passwordResetToken.getUser() == null || passwordResetToken.getUser().getUserId() != userId) {
            Response response = new Response(ResponseStatus.FAILED, "This token does not belong to you");
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }

        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, period);
        Date max = c.getTime();
        Date min = passwordResetToken.getCreationDate();
        Date currentDate = new Date();

        if (!(currentDate.after(min) && currentDate.before(max))) {
            Response response = new Response(ResponseStatus.FAILED, "This token has been expired, please request for a new one");
            ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
            return responseEntity;
        }


        Response response = new Response(ResponseStatus.SUCCESSFUL, "This token is ready to use");
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);

        return responseEntity;

    }

    private String constructMessage(String contextPath, PasswordResetToken passwordResetToken) {
        //TODO for the other clients
//        if(context.equals("NONE")){
//            resetPasswordUrl = getBaseUrl();
//        }

        Long userId = passwordResetToken.getUser().getUserId();
        String token = passwordResetToken.getToken();

        String urlFormat = "%s/change-password?id=%d&token=%s";
        String url = String.format(urlFormat, contextPath, userId, token);

        StringBuilder sb = new StringBuilder();
        sb
                .append("This email is an automatic email coming from DepEd Inventory System upon your request:\n\n")
                .append("Please Change Your Password Using the below URL below:\n\n")
                .append(url);
        String message = sb.toString();

        return message;
    }
}
