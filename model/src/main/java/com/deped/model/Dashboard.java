package com.deped.model;


public class Dashboard {

    private Long lastWeekRequest;
    private Long lastWeekOrders;
    private Long underThreshold;
    private Long totalUsers;

    public Dashboard() {
    }

    public Dashboard(Long lastWeekRequest, Long lastWeekOrders, Long underThreshold, Long totalUsers) {
        this.lastWeekRequest = lastWeekRequest;
        this.lastWeekOrders = lastWeekOrders;
        this.underThreshold = underThreshold;
        this.totalUsers = totalUsers;
    }

    public Long getLastWeekRequest() {
        return lastWeekRequest;
    }

    public void setLastWeekRequest(Long lastWeekRequest) {
        this.lastWeekRequest = lastWeekRequest;
    }

    public Long getLastWeekOrders() {
        return lastWeekOrders;
    }

    public void setLastWeekOrders(Long lastWeekOrders) {
        this.lastWeekOrders = lastWeekOrders;
    }

    public Long getUnderThreshold() {
        return underThreshold;
    }

    public void setUnderThreshold(Long underThreshold) {
        this.underThreshold = underThreshold;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }
}
