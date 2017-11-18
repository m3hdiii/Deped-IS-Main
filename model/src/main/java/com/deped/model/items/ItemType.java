package com.deped.model.items;

/**
 * Created by mehdi on 7/6/17.
 */
public enum ItemType {

    SEMI_EXPENDABLE("Semi Expendable"),
    GOODS("Goods"),
    EQUIPMENT("Equipment");

    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
