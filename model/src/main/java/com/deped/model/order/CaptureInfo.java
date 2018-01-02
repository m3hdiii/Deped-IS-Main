package com.deped.model.order;

import com.deped.model.items.ItemDetails;

import java.io.Serializable;
import java.util.List;

public class CaptureInfo implements Serializable {


    private Long orderDetailsConstantKey;

    //    @Column(name = "inserted_equipment")
    private Long numberOfCapturedItems;

    //    @Column(name = "must_insert")
    private Long numberOfRemainingCapturedItems;

    //    @Column(name = "arrived_number")
    private Long numberOfArrivedItems;


    private Long totalReuqestedQuantity;

    //    @Column(name = "item_name")
    private String itemName;

    private String pictureName;

    private Long quantityAvailableInSupplyOffice;

    private List<ItemDetails> itemDetailsList;


    //    @Column(name = "order_details")

    public CaptureInfo() {
    }

    public CaptureInfo(Long orderDetailsConstantKey, Long numberOfCapturedItems, Long numberOfRemainingCapturedItems, Long numberOfArrivedItems, Long totalReuqestedQuantity, String itemName, String pictureName, Long quantityAvailableInSupplyOffice) {
        this.orderDetailsConstantKey = orderDetailsConstantKey;
        this.numberOfCapturedItems = numberOfCapturedItems;
        this.numberOfRemainingCapturedItems = numberOfRemainingCapturedItems;
        this.numberOfArrivedItems = numberOfArrivedItems;
        this.totalReuqestedQuantity = totalReuqestedQuantity;
        this.itemName = itemName;
        this.pictureName = pictureName;
        this.quantityAvailableInSupplyOffice = quantityAvailableInSupplyOffice;
    }

    public Long getOrderDetailsConstantKey() {
        return orderDetailsConstantKey;
    }

    public void setOrderDetailsConstantKey(Long orderDetailsConstantKey) {
        this.orderDetailsConstantKey = orderDetailsConstantKey;
    }

    public Long getNumberOfCapturedItems() {
        return numberOfCapturedItems;
    }

    public void setNumberOfCapturedItems(Long numberOfCapturedItems) {
        this.numberOfCapturedItems = numberOfCapturedItems;
    }

    public Long getNumberOfRemainingCapturedItems() {
        return numberOfRemainingCapturedItems;
    }

    public void setNumberOfRemainingCapturedItems(Long numberOfRemainingCapturedItems) {
        this.numberOfRemainingCapturedItems = numberOfRemainingCapturedItems;
    }

    public Long getNumberOfArrivedItems() {
        return numberOfArrivedItems;
    }

    public void setNumberOfArrivedItems(Long numberOfArrivedItems) {
        this.numberOfArrivedItems = numberOfArrivedItems;
    }

    public Long getTotalReuqestedQuantity() {
        return totalReuqestedQuantity;
    }

    public void setTotalReuqestedQuantity(Long totalReuqestedQuantity) {
        this.totalReuqestedQuantity = totalReuqestedQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Long getQuantityAvailableInSupplyOffice() {
        return quantityAvailableInSupplyOffice;
    }

    public void setQuantityAvailableInSupplyOffice(Long quantityAvailableInSupplyOffice) {
        this.quantityAvailableInSupplyOffice = quantityAvailableInSupplyOffice;
    }

    public List<ItemDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }
}
