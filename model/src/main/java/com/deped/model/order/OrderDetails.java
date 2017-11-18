package com.deped.model.order;

import com.deped.model.account.User;
import com.deped.model.category.Category;
import com.deped.model.items.Item;
import com.deped.model.pack.Pack;
import com.deped.model.supply.Supplier;
import com.deped.protection.validators.decimal.DoubleRange;
import com.deped.protection.validators.integer.IntegerRange;
import com.deped.protection.validators.xss.XSS;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;


@Entity
@Table(name = "order_details")
@DynamicUpdate
public class OrderDetails implements Serializable {


    @EmbeddedId
    private OrderDetailsID orderDetailsID = new OrderDetailsID();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_order_id")
    @MapsId("orderId")
//    @JsonBackReference(value = "order-orderDetails")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @MapsId("itemId")
    @JoinColumn(name = "item_item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @MapsId("categoryId")
    @JoinColumn(name = "category_category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    @Column(name = "item_unit_price")
    @DoubleRange(min = 1.0, message = "Unit price must have a value more than 1 Peso")
    private Double unitPrice;

    @Column(name = "package_capacity")
    @Min(value = 1, message = "package can not have a negative capacity")
    @Max(value = 5000, message = "package can not have more than 5000 capacity")
    private Integer packCapacity;

    @Column(name = "no_of_packs")
    @Min(value = 1, message = "Number of packages can not be negative")
    @Max(value = 500, message = "Number of packages can not be more than 500")
    private Integer noOfPacks;

    @Column(name = "total_quantity_requested_no")
    @IntegerRange(min = 1, max = 2500000, mandatory = true, message = "Total quantity must be between 1 and 2500000")
    private Integer totalQuantityRequestNo;

    @Column(name = "total_quantity_arrived_no")
    @Min(value = 1, message = "Total quantity arrived can not be a negative")
    @Max(value = 2500000, message = "Total quantity arrived can not be more than 2500000")
    private Integer totalQuantityArrivedNo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_details_state")
    private OrderDetailsState orderDetailsState;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "disapproval_message")
    @Length(min = 1, max = 1000, message = "Disapproval Message field must not be more than 1000 character")
    @XSS
    private String disapprovalMessage;

    @Column(name = "not_arrival_message")
    @Length(min = 1, max = 1000, message = "Disapproval Message field must not be more than 1000 character")
    @XSS
    private String notArrivalMessage;

    @Transient
    private OrderDetailsState transientUpdateState;

    @JoinColumn(name = "considered_by_user_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User consideredByUser;

    @JoinColumn(name = "ordered_by_user_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User orderedByUser;

    @JoinColumn(name = "received_by_user_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User receivedByUser;

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

    public Integer getNoOfPacks() {
        return noOfPacks;
    }

    public void setNoOfPacks(Integer noOfPacks) {
        this.noOfPacks = noOfPacks;
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

    public OrderDetailsState getTransientUpdateState() {
        return transientUpdateState;
    }

    public void setTransientUpdateState(OrderDetailsState transientUpdateState) {
        this.transientUpdateState = transientUpdateState;
    }

    public User getConsideredByUser() {
        return consideredByUser;
    }

    public void setConsideredByUser(User consideredByUser) {
        this.consideredByUser = consideredByUser;
    }

    public User getOrderedByUser() {
        return orderedByUser;
    }

    public void setOrderedByUser(User orderedByUser) {
        this.orderedByUser = orderedByUser;
    }

    public User getReceivedByUser() {
        return receivedByUser;
    }

    public void setReceivedByUser(User receivedByUser) {
        this.receivedByUser = receivedByUser;
    }
}
