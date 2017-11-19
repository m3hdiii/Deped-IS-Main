package com.deped.model.items.features;

public enum Colour {
    RED("Red"),
    BLUE("Blue"),
    BLACK("Black"),
    WHITE("White"),
    GRAY("Gray"),
    SILVER("Silver"),
    PINK("Pink"),
    YELLOW("Yellow"),
    BROWN("Brown"),
    CREAM("Cream"),
    ORANGE("Orange");

    private final String name;

    Colour(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
