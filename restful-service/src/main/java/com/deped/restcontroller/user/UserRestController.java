package com.deped.restcontroller.user;

import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.model.Operation;
import com.deped.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = FETCH_MAPPING)
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

    @RequestMapping(value = "user/fetch/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> fetchByUsername(@PathVariable("username") String username) {
        User user = userService.fetchByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
