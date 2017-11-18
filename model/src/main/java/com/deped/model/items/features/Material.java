package com.deped.model.items.features;

/**
 * Created by mehdi on 7/6/17.
 */
public enum Material {
    MONOBLOCK("Mono block"),
    WOOD("Wood"),
    METAL("Metal");

    private final String name;

    Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
