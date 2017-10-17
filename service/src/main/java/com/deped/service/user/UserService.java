package com.deped.service.user;

import com.deped.model.account.User;
import com.deped.service.BaseService;

public interface UserService extends BaseService<User> {

    boolean isUserAlreadyExist(User user);


    User loginUser(User user);

    User fetchByUsername(String username);
}
