package com.deped.model.account;

/**
 * Created by Mehdi on 6/8/2017.
 */
public enum Gender {
    FEMALE("Female"), MALE("Male");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getValue() {
        return name();
    }

    public void setValue(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
