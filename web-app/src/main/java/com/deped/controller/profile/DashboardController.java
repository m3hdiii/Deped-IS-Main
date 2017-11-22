package com.deped.controller.profile;

import com.deped.controller.SharedData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {

    private static final String DASHBOARD = "dashboard";
    private static final String REFRESH_ALL = "refresh-all";

    @RequestMapping(value = DASHBOARD, method = RequestMethod.GET)
    public String showHome(RedirectAttributes redirectAttributes) {
        return "center/dashboard";
    }

    @RequestMapping(value = REFRESH_ALL, method = RequestMethod.GET)
    public ModelAndView refreshAll() {
        SharedData.refreshAll();
        ModelAndView mav = new ModelAndView("center/dashboard", "dataUpdateSuccess", "All data successfully updated");
        return mav;

    }
}
