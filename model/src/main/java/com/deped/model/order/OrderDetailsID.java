package com.deped.model.order;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class OrderDetailsID implements Serializable{

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_id")
    private Long itemId;


    public OrderDetailsID() {
    }

    public OrderDetailsID(Long orderId, Long itemId, Long categoryId) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.categoryId = categoryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
