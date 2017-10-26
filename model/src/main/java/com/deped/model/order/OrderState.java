package com.deped.model.order;

public enum OrderState {
    ORDERING, //when it's inside the process of ordering and can edit the orders
    ORDERS_REGISTERED, //when he/she decide about all items and want to register the order
    ALL_APPROVED, //when all items inside "registered order" approved
    PARTIALLY_APPROVED, //when some items inside "registered order" approved
    NOT_APPROVED, //when none of the items inside "registered order" approved
    ORDERED, //when he/she finished calling the suppliers and ordered the items
    ALL_ARRIVED, //when all items arrived
    PARTIALLY_ARRIVED, //when some items were not available from supplier part and they couldn't provide them
    NOT_ARRIVED //when something totally went wrong and none of the items could arrive (RARELY HAPPEN)
}
