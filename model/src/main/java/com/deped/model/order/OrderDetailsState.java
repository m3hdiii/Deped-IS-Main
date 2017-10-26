package com.deped.model.order;

public enum OrderDetailsState {
    WAITING, //when order for that specific item is waiting to get approve
    APPROVED, //when order for that specific item got approved
    DISAPPROVED, // when order for that specific item got disapproved
    ARRIVED, //when order for that specific item arrived
    NOT_ARRIVED //when order for that specific item didn't arrived (this is a problem from supplier part)
}
