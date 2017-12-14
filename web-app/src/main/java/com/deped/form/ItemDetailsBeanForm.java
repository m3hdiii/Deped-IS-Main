package com.deped.form;

import java.util.List;

public class ItemDetailsBeanForm {

    private List<ItemDetailsBean> itemDetailsBeans;

    public ItemDetailsBeanForm(List<ItemDetailsBean> itemDetailsBeans) {
        this.itemDetailsBeans = itemDetailsBeans;
    }

    public List<ItemDetailsBean> getItemDetailsBeans() {
        return itemDetailsBeans;
    }

    public void setItemDetailsBeans(List<ItemDetailsBean> itemDetailsBeans) {
        this.itemDetailsBeans = itemDetailsBeans;
    }
}
