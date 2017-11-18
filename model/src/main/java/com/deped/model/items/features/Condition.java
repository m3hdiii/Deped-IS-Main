package com.deped.model.items.features;

public enum Condition {
    OK("Ok"),
    UNREPAIRABLE("Unrepairable"),
    DAMAGED("Damaged"),
    LOST("Lost");

    private final String name;

    Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
