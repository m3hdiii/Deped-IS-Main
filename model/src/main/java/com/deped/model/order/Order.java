package com.deped.model.order;

import com.deped.model.account.User;
import com.deped.model.delivery.DeliveryInformation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "order_")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "required_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requiredDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_schedule")
    @Enumerated(EnumType.STRING)
    private Schedule orderSchedule;

    @Column(name = "budget_amount")
    private Double budgetAmount;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
//    @JsonManagedReference(value = "order-orderDetails")
//    private Set<OrderDetails> orderDetails;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<DeliveryInformation> deliveryInformation;

    @Column(name = "order_state")
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @Column(name = "arrival_description")
    private String arrivalDescription;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Schedule getOrderSchedule() {
        return orderSchedule;
    }

    public void setOrderSchedule(Schedule orderSchedule) {
        this.orderSchedule = orderSchedule;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

//    public Set<OrderDetails> getOrderDetails() {
//        return orderDetails;
//    }
//
//    public void setOrderDetails(Set<OrderDetails> orderDetails) {
//        this.orderDetails = orderDetails;
//    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public List<DeliveryInformation> getDeliveryInformation() {
        return deliveryInformation;
    }

    public void setDeliveryInformation(List<DeliveryInformation> deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getArrivalDescription() {
        return arrivalDescription;
    }

    public void setArrivalDescription(String arrivalDescription) {
        this.arrivalDescription = arrivalDescription;
    }
}
