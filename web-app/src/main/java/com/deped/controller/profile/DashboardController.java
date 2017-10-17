package com.deped.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private static final String DASHBOARD = "dashboard";

    @RequestMapping(value = DASHBOARD, method = RequestMethod.GET)
    public String showHome() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true); // true == allow create
        return "center/dashboard";
    }
}
