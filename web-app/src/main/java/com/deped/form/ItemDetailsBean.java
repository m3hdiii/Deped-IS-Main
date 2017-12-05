package com.deped.form;

import com.deped.model.items.ItemDetails;
import com.deped.model.order.CaptureInfo;

import java.util.List;

public class ItemDetailsBean {
    private List<ItemDetails> itemDetailsList;
    private CaptureInfo captureInfo;

    public ItemDetailsBean() {
    }

    public ItemDetailsBean(List<ItemDetails> itemDetailsList, CaptureInfo captureInfo) {
        this.itemDetailsList = itemDetailsList;
        this.captureInfo = captureInfo;
    }

    public List<ItemDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }

    public CaptureInfo getCaptureInfo() {
        return captureInfo;
    }

    public void setCaptureInfo(CaptureInfo captureInfo) {
        this.captureInfo = captureInfo;
    }
}
