package com.deped.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {

    private static final String DASHBOARD = "dashboard";

    @RequestMapping(value = DASHBOARD, method = RequestMethod.GET)
    public String showHome(RedirectAttributes redirectAttributes) {
        return "center/dashboard";
    }
}
