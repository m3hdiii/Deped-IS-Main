package com.deped.model.items.features;

public enum EquipmentAvailability {
    AVAILABLE("Available"), HELD("Held"), NOT_AVAILABLE("Not Available");

    private final String name;

    EquipmentAvailability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
