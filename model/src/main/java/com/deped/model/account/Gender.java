package com.deped.model.account;

/**
 * Created by Mehdi on 6/8/2017.
 */
public enum Gender {
    FEMALE("Female"),
    MALE("Male");

    private String gender;

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
