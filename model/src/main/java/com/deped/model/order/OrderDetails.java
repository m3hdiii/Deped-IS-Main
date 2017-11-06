package com.deped.model.order;

import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.pack.Pack;
import com.deped.model.supply.Supplier;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "order_details")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "orderDetailsID", scope = OrderDetails.class)
public class OrderDetails implements Serializable {


    @EmbeddedId
    private OrderDetailsID orderDetailsID = new OrderDetailsID();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_order_id")
    @MapsId("orderId")
    @JsonBackReference(value = "order-orderDetails")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("itemId")
    @JoinColumn(name = "item_item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryId")
    @JoinColumn(name = "category_category_id")
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
    @Column(name = "order_details_state")
    private OrderDetailsState orderDetailsState;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "disapproval_message")
    private String disapprovalMessage;

    @Column(name = "not_arrival_message")
    private String notArrivalMessage;

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

    public OrderDetailsState getOrderDetailsState() {
        return orderDetailsState;
    }

    public void setOrderDetailsState(OrderDetailsState orderDetailsState) {
        this.orderDetailsState = orderDetailsState;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getDisapprovalMessage() {
        return disapprovalMessage;
    }

    public void setDisapprovalMessage(String disapprovalMessage) {
        this.disapprovalMessage = disapprovalMessage;
    }

    public String getNotArrivalMessage() {
        return notArrivalMessage;
    }

    public void setNotArrivalMessage(String notArrivalMessage) {
        this.notArrivalMessage = notArrivalMessage;
    }
}
