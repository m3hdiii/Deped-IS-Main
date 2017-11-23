package com.deped.restcontroller.other;

import com.deped.model.Dashboard;
import com.deped.service.other.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardRestController {

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping("/dashboard")
    public Dashboard getBasicInfo() {
        return dashboardService.getBasicInfo();
    }
}
