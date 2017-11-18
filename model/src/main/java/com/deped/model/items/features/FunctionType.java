package com.deped.model.items.features;

public enum FunctionType {

    ELECTRICAL("Electrical"),
    NON_ELECTRICAL("Non Electrical");

    private final String name;

    FunctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
