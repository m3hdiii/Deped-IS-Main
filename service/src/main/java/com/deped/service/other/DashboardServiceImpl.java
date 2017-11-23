package com.deped.service.other;

import com.deped.model.Dashboard;
import com.deped.repository.other.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    public Dashboard getBasicInfo() {
        return dashboardRepository.getBasicInfo();
    }
}
