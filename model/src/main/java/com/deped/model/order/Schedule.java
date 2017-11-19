package com.deped.model.order;

public enum Schedule {

    OUT_OF_SCHEDULE("Out Of Schedule"),
    FIRST_QUARTER("First Quarter"),
    SECOND_QUARTER("Second Quarter"),
    THIRD_QUARTER("Third Quarter"),
    FOURTH_QUARTER("Fourth Quarter");

    private final String name;

    Schedule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
