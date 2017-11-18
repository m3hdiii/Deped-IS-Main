package com.deped.model.account;

public enum Position {
    PERSONNEL("Personnel"),
    HEAD("Head"),
    MANAGER("Manager"),
    ASSOCIATE_MANAGER("Associate Manager"),
    OFFICER("Officer");

    private final String name;

    Position(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
