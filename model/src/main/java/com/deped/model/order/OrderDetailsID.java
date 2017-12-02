package com.deped.model.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailsID implements Serializable{

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_name")
    private String itemName;


    public OrderDetailsID() {
    }

    public OrderDetailsID(Long orderId, String itemName, String categoryName) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.categoryName = categoryName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
