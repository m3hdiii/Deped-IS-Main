package com.deped.form;

import com.deped.model.order.OrderDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * This is only for Spring command object within JSP
 */
public class OrderDetailsForm {
    private Map<String, OrderDetails> map = new HashMap<>();

    public Map<String, OrderDetails> getMap() {
        return map;
    }

    public void setMap(Map<String, OrderDetails> map) {
        this.map = map;
    }
}
