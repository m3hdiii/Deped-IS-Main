package com.deped.restcontroller.user;

import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<User> create(@RequestBody User entity) {
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

    @RequestMapping(value = "/user/reset-password/", method = RequestMethod.POST)
    public ResponseEntity<Response> resetPassword(@RequestBody String[] vars) {
        String context = vars[0];
        String emailAddress = vars[1];

        User user = userService.fetchByEmail(emailAddress);
        if (user == null) {

        }

        ResponseEntity<Response> response = userService.createPasswordResetTokenForUser(user, context);
        return response;
    }

    @RequestMapping(value = "/user/check-token/", method = RequestMethod.POST)
    public ResponseEntity<Response> checkToken(@RequestBody String[] values) {
        ResponseEntity<Response> response = userService.checkToken(Long.valueOf(values[0]), values[1]);
        return response;
    }

    @RequestMapping(value = "/user/change-password/", method = RequestMethod.POST)
    public ResponseEntity<Response> changePassword(@RequestBody String[] values) {
        ResponseEntity<Response> response = userService.changePasswordByToken(Long.valueOf(values[0]), values[1], values[2]);
        return response;
    }



    public String getBaseUrl() {
        String formatUrl = "%s://%s%s%s";
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String url = String.format(formatUrl, request.getScheme(), request.getServerName(), serverPort, request.getContextPath());
        return url;
    }

}
