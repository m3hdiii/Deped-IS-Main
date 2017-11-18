package com.deped.model.location;

public enum Continent {
    ASIA("Asia"),
    EUROPE("Europe"),
    NORTH_AMERICA("North America"),
    AFRICA("Africa"),
    OCEANIA("Oceania"),
    ANTARCTICA("Antarctica"),
    SOUTH_AMERICA("South America");
    private final String name;

    Continent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
