package com.deped.service.user;


import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.account.User;
import com.deped.repository.user.LoginMethod;
import com.deped.repository.user.UserRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
