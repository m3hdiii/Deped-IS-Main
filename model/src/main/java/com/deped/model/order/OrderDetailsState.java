package com.deped.model.order;

public enum OrderDetailsState {
    WAITING("Waiting", "Wait"), //when order for that specific item is waiting to get approve
    APPROVED("Approved", "Approve"), //when order for that specific item got approved
    DISAPPROVED("Disapproved", "Disapprove"), // when order for that specific item got disapproved
    ORDERED("Ordered", "Order"), //when supply officer is ordered and waiting to receive the Item
    ARRIVED("Arrived", "Arrived"), //when order for that specific item arrived
    PARTIALLY_ARRIVED("Partially Arrived", "Partially Arrived"), //When we have partial delivery
    NOT_ARRIVED("Not Arrived", "Not Arrived"), //when order for that specific item didn't arrived (this is a problem from supplier part)
    CANCELED("Canceled", "Cancel");

    private final String name;
    private final String action;

    OrderDetailsState(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }
}
