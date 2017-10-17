package com.deped.restcontroller.profile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profileGet() {
        return "center/profile";
    }

    @RequestMapping(value = "/edituserprofile", method = RequestMethod.POST)
    public String editUserProfile() {
        return "center/edituserprofile";
    }

    @RequestMapping(value = {"/dashboard"}, method = RequestMethod.POST)
    public String showHome() {
        return "center/dashboard";
    }
}
