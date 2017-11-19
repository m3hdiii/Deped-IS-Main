package com.deped.model.request;

public enum RequestStatus {
    SAVED("Saved"),
    PENDING("Pending"),
    CONSIDERED("Considered"),
    FINALIZED("Finalized");

    private final String name;

    RequestStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
