package com.deped.controller.login;

import com.deped.model.account.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private static final String LOGIN = "login";
    private static final String HOME = "home";
    private static final String INDEX = "index";
    private static final String MAIN = "main";


    @RequestMapping(value = {"", HOME, INDEX, MAIN, LOGIN}, method = RequestMethod.GET)
    public String renderLogin(@ModelAttribute("user") User user) {
        return "center/login";
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public void checkLogin(@RequestBody User user) {

    }
}
