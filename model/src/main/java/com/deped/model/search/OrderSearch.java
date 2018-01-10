package com.deped.model.search;

import com.deped.model.account.User;
import com.deped.model.category.Category;
import com.deped.model.delivery.DeliveryInformation;
import com.deped.model.items.Item;
import com.deped.model.order.OrderDetailsState;
import com.deped.model.order.OrderState;
import com.deped.model.order.Schedule;
import com.deped.model.supply.Supplier;
import com.deped.model.unit.Unit;

import java.util.Date;
import java.util.List;

public class OrderSearch {

    private Date orderDateFrom;
    private Date orderDateTo;
    private Date requiredDateFrom;
    private Date requiredDateTo;
    private List<User> users;
    private List<Schedule> orderSchedules;
    private Double budgetAmountFrom;
    private Double budgetAmountTo;
    private List<DeliveryInformation> deliveryInformation;
    private List<OrderState> orderStates;
    private String arrivalDescription;
    private List<Item> items;
    private List<Category> categories;
    private List<Unit> units;
    private Double unitPriceFrom;
    private Double unitPriceTo;
    private Integer unitCapacityFrom;
    private Integer unitCapacityTo;
    private Integer noOfUnitsFrom;
    private Integer noOfUnitsTo;
    private Integer totalQuantityRequestNoFrom;
    private Integer totalQuantityRequestNoTo;
    private Integer totalQuantityArrivedNoFrom;
    private Integer totalQuantityArrivedNoTo;
    private List<OrderDetailsState> orderDetailsStates;
    private List<Supplier> suppliers;
    private String disapprovalMessage;
    private String notArrivalMessage;
    private List<User> consideredByUsers;
    private List<User> orderedByUsers;
    private List<User> receivedByUsers;

    public Date getOrderDateFrom() {
        return orderDateFrom;
    }

    public void setOrderDateFrom(Date orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }

    public Date getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public Date getRequiredDateFrom() {
        return requiredDateFrom;
    }

    public void setRequiredDateFrom(Date requiredDateFrom) {
        this.requiredDateFrom = requiredDateFrom;
    }

    public Date getRequiredDateTo() {
        return requiredDateTo;
    }

    public void setRequiredDateTo(Date requiredDateTo) {
        this.requiredDateTo = requiredDateTo;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Schedule> getOrderSchedules() {
        return orderSchedules;
    }

    public void setOrderSchedules(List<Schedule> orderSchedules) {
        this.orderSchedules = orderSchedules;
    }

    public Double getBudgetAmountFrom() {
        return budgetAmountFrom;
    }

    public void setBudgetAmountFrom(Double budgetAmountFrom) {
        this.budgetAmountFrom = budgetAmountFrom;
    }

    public Double getBudgetAmountTo() {
        return budgetAmountTo;
    }

    public void setBudgetAmountTo(Double budgetAmountTo) {
        this.budgetAmountTo = budgetAmountTo;
    }

    public List<DeliveryInformation> getDeliveryInformation() {
        return deliveryInformation;
    }

    public void setDeliveryInformation(List<DeliveryInformation> deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }

    public List<OrderState> getOrderStates() {
        return orderStates;
    }

    public void setOrderStates(List<OrderState> orderStates) {
        this.orderStates = orderStates;
    }

    public String getArrivalDescription() {
        return arrivalDescription;
    }

    public void setArrivalDescription(String arrivalDescription) {
        this.arrivalDescription = arrivalDescription;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public Double getUnitPriceFrom() {
        return unitPriceFrom;
    }

    public void setUnitPriceFrom(Double unitPriceFrom) {
        this.unitPriceFrom = unitPriceFrom;
    }

    public Double getUnitPriceTo() {
        return unitPriceTo;
    }

    public void setUnitPriceTo(Double unitPriceTo) {
        this.unitPriceTo = unitPriceTo;
    }

    public Integer getUnitCapacityFrom() {
        return unitCapacityFrom;
    }

    public void setUnitCapacityFrom(Integer unitCapacityFrom) {
        this.unitCapacityFrom = unitCapacityFrom;
    }

    public Integer getUnitCapacityTo() {
        return unitCapacityTo;
    }

    public void setUnitCapacityTo(Integer unitCapacityTo) {
        this.unitCapacityTo = unitCapacityTo;
    }

    public Integer getNoOfUnitsFrom() {
        return noOfUnitsFrom;
    }

    public void setNoOfUnitsFrom(Integer noOfUnitsFrom) {
        this.noOfUnitsFrom = noOfUnitsFrom;
    }

    public Integer getNoOfUnitsTo() {
        return noOfUnitsTo;
    }

    public void setNoOfUnitsTo(Integer noOfUnitsTo) {
        this.noOfUnitsTo = noOfUnitsTo;
    }

    public Integer getTotalQuantityRequestNoFrom() {
        return totalQuantityRequestNoFrom;
    }

    public void setTotalQuantityRequestNoFrom(Integer totalQuantityRequestNoFrom) {
        this.totalQuantityRequestNoFrom = totalQuantityRequestNoFrom;
    }

    public Integer getTotalQuantityRequestNoTo() {
        return totalQuantityRequestNoTo;
    }

    public void setTotalQuantityRequestNoTo(Integer totalQuantityRequestNoTo) {
        this.totalQuantityRequestNoTo = totalQuantityRequestNoTo;
    }

    public Integer getTotalQuantityArrivedNoFrom() {
        return totalQuantityArrivedNoFrom;
    }

    public void setTotalQuantityArrivedNoFrom(Integer totalQuantityArrivedNoFrom) {
        this.totalQuantityArrivedNoFrom = totalQuantityArrivedNoFrom;
    }

    public Integer getTotalQuantityArrivedNoTo() {
        return totalQuantityArrivedNoTo;
    }

    public void setTotalQuantityArrivedNoTo(Integer totalQuantityArrivedNoTo) {
        this.totalQuantityArrivedNoTo = totalQuantityArrivedNoTo;
    }

    public List<OrderDetailsState> getOrderDetailsStates() {
        return orderDetailsStates;
    }

    public void setOrderDetailsStates(List<OrderDetailsState> orderDetailsStates) {
        this.orderDetailsStates = orderDetailsStates;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
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

    public List<User> getConsideredByUsers() {
        return consideredByUsers;
    }

    public void setConsideredByUsers(List<User> consideredByUsers) {
        this.consideredByUsers = consideredByUsers;
    }

    public List<User> getOrderedByUsers() {
        return orderedByUsers;
    }

    public void setOrderedByUsers(List<User> orderedByUsers) {
        this.orderedByUsers = orderedByUsers;
    }

    public List<User> getReceivedByUsers() {
        return receivedByUsers;
    }

    public void setReceivedByUsers(List<User> receivedByUsers) {
        this.receivedByUsers = receivedByUsers;
    }
}
