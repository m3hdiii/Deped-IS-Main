package com.deped.form;

import com.deped.model.items.ItemDetails;

import java.util.List;

public class ItemDetailsForm {

    private List<ItemDetails> itemDetailsList;

    public ItemDetailsForm() {
    }

    public ItemDetailsForm(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }

    public List<ItemDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }
}
