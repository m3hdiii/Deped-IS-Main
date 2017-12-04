package com.deped.model.order;

public enum OrderState {
    SAVED("Saved"),
    PENDING("Pending"),
    CONSIDERED("Considered"),
    ORDERED("Ordered"),
    PARTIALLY_ARRIVED("Partially Arrived"),
    FINALIZED("Finalized");

    private final String name;

    OrderState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
