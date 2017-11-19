package com.deped.service.user;

import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

public interface UserService extends BaseService<User> {

    boolean isUserAlreadyExist(User user);

    User loginUser(User user);

    User fetchByUsername(String username);

    User fetchByEmail(String email);

    ResponseEntity<Response> createPasswordResetTokenForUser(User user, String context);

    ResponseEntity<Response> checkToken(Long userId, String token);


    ResponseEntity<Response> changePasswordByToken(Long userId, String token, String newPassword);
}
