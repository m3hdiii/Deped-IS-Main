package com.deped.restcontroller.login;

import com.deped.model.account.User;
import com.deped.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private static final String LOGIN = "login";
    private static final String HOME = "home";
    private static final String INDEX = "index";
    private static final String MAIN = "main";

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", HOME, INDEX, MAIN, LOGIN}, method = RequestMethod.POST)
    public String renderLogin(@ModelAttribute("user") User user) {
        return "center/login";
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public void checkLogin(@RequestBody User user) {
        userService.loginUser(user);
    }
}
