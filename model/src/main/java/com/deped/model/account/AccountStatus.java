package com.deped.model.account;

/**
 * Created by mehdi on 3/17/17.
 */
public enum AccountStatus {
    EXPIRED("Expired"),
    ACTIVE("Active"),
    LOCKED("Locked"),
    NOT_ACTIVE("Not Active");

    private final String name;

    AccountStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
