package com.deped.model.tracker;

public enum TrackingStatus {
    IN_USE("In Use"),
    RETURNED("Returned"),
    UN_RETURNABLE("Un Returned");

    private final String name;

    TrackingStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
