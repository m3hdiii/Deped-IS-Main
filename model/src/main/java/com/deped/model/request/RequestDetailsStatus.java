package com.deped.model.request;

/**
 * Created by mehdi on 7/6/17.
 */
public enum RequestDetailsStatus {
    WAITING("Waiting", "Wait"),
    APPROVED("Approved", "Approve"),
    DISAPPROVED("Disapproved", "Disapprove"),
    RELEASED("Released", "Released"),
    CANCELED("Canceled", "Cancel");

    private final String name;
    private final String action;

    RequestDetailsStatus(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }
}
