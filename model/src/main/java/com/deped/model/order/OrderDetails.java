package com.deped.model.order;

import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.pack.Pack;
import com.deped.model.supply.Supplier;

import javax.persistence.*;


@Entity
@Table(name = "order_details")
public class OrderDetails {


    @EmbeddedId
    private OrderDetailsID orderDetailsID = new OrderDetailsID();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    @Column(name = "item_unit_price")
    private Double unitPrice;

    @Column(name = "package_capacity")
    private Integer packCapacity;

    @Column(name = "total_quantity_requested_no")
    private Integer totalQuantityRequestNo;

    @Column(name = "total_quantity_arrived_no")
    private Integer totalQuantityArrivedNo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_state")
    private OrderState orderState;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public OrderDetailsID getOrderDetailsID() {
        return orderDetailsID;
    }

    public void setOrderDetailsID(OrderDetailsID orderDetailsID) {
        this.orderDetailsID = orderDetailsID;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getPackCapacity() {
        return packCapacity;
    }

    public void setPackCapacity(Integer packCapacity) {
        this.packCapacity = packCapacity;
    }

    public Integer getTotalQuantityRequestNo() {
        return totalQuantityRequestNo;
    }

    public void setTotalQuantityRequestNo(Integer totalQuantityRequestNo) {
        this.totalQuantityRequestNo = totalQuantityRequestNo;
    }

    public Integer getTotalQuantityArrivedNo() {
        return totalQuantityArrivedNo;
    }

    public void setTotalQuantityArrivedNo(Integer totalQuantityArrivedNo) {
        this.totalQuantityArrivedNo = totalQuantityArrivedNo;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
