package com.deped.model.items.type;

import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.items.features.FunctionType;
import com.deped.model.items.features.Material;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Entity
@DiscriminatorValue("EQUIPMENT")
public class Equipment extends Item implements Serializable{

    public Equipment(){
        setItemType(ItemType.EQUIPMENT);
    }

    public void setFunctionType(FunctionType functionType) {
        this.functionType = functionType;
    }

    public void setItemDetails(List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
