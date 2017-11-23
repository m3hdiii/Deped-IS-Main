package com.deped.controller.profile;

import com.deped.controller.SharedData;
import com.deped.model.Dashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DashboardController {

    private static final String DASHBOARD = "dashboard";
    private static final String REFRESH_ALL = "refresh-all";

    @RequestMapping(value = DASHBOARD, method = RequestMethod.GET)
    public ModelAndView showHome(RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        String restUrl = SharedData.getRestBaseUrl() + "dashboard";
        ResponseEntity<Dashboard> response = restTemplate.getForEntity(restUrl, Dashboard.class);

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("dashboardInfo", response.getBody());
        ModelAndView mav = new ModelAndView("center/dashboard", modelMap);
        return mav;
    }

    @RequestMapping(value = REFRESH_ALL, method = RequestMethod.GET)
    public ModelAndView refreshAll() {
        SharedData.refreshAll();
        ModelAndView mav = new ModelAndView("center/dashboard", "dataUpdateSuccess", "All data successfully updated");
        return mav;
    }
}
