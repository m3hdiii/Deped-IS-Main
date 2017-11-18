package com.deped.restcontroller.user;

import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.model.security.PasswordResetToken;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.email.EmailService;
import com.deped.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by mehdi on 7/7/17.
 */

@RestController
public class UserRestController extends AbstractMainRestController<User, Long> {

    private static final String BASE_NAME = "user";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;


    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<User> create(User entity) {
        ResponseEntity<User> response = userService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody User entity) {
        ResponseEntity<Response> response = userService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<User>> fetchAll() {
        ResponseEntity<List<User>> response = userService.fetchAll();
        return response;
    }


    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<User>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<User>> response = userService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<User> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<User> response = userService.fetchById(aLong);
        return response;
    }

    @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    @Override
    public ResponseEntity<Response> remove(@RequestBody User... users) {
        ResponseEntity<Response> response = userService.remove(users);
        return response;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(User... entities) {
        return null;
    }

    @RequestMapping(value = "user/fetch/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> fetchByUsername(@PathVariable("username") String username) {
        User user = userService.fetchByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/resetPassword/{email}/{context}", method = RequestMethod.POST)
    public ResponseEntity<PasswordResetToken> resetPassword(@PathVariable("email") String userEmail, @PathVariable("context") String context) {


        User user = userService.fetchByEmail(userEmail);
        if (user == null) {
            throw new NullPointerException();
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = userService.createPasswordResetTokenForUser(user, token);

        String message = constructMessage(context, passwordResetToken);

        EmailService.sendEmail(userEmail, "Deped-IS Change Password", message);
        return null;
    }

    private String constructMessage(String contextPath, PasswordResetToken passwordResetToken) {
        //TODO for the other clients
//        if(context.equals("NONE")){
//            resetPasswordUrl = getBaseUrl();
//        }

        Long userId = passwordResetToken.getUser().getUserId();
        String token = passwordResetToken.getToken();

        String urlFormat = "%s/user/changePassword?id=%d&token=%s";
        String url = String.format(urlFormat, contextPath, userId, token);

        StringBuilder sb = new StringBuilder();
        sb
                .append("This email is an automatic email coming from DepEd Inventory System upon your request:\n\n")
                .append("Please Change Your Password Using the below URL below:\n\n")
                .append(url);
        String message = sb.toString();

        return message;
    }

    public String getBaseUrl() {
        String formatUrl = "%s://%s%s%s";
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String url = String.format(formatUrl, request.getScheme(), request.getServerName(), serverPort, request.getContextPath());
        return url;
    }

}
