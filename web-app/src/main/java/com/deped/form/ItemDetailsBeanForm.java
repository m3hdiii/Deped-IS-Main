package com.deped.form;

import com.deped.model.order.CaptureInfo;

import java.util.List;

public class ItemDetailsBeanForm {
    private List<CaptureInfo> captureInfoList;

    public ItemDetailsBeanForm() {
    }

    public ItemDetailsBeanForm(List<CaptureInfo> captureInfoList) {
        this.captureInfoList = captureInfoList;
    }

    public List<CaptureInfo> getCaptureInfoList() {
        return captureInfoList;
    }

    public void setCaptureInfoList(List<CaptureInfo> captureInfoList) {
        this.captureInfoList = captureInfoList;
    }
}
