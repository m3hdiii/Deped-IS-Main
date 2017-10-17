package com.deped.model.items.type;

import com.deped.model.items.Item;
import com.deped.model.items.ItemDetails;
import com.deped.model.items.ItemType;
import com.deped.model.items.features.FunctionType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Entity
@DiscriminatorValue("SEMI_EXPENDABLE")
public class SemiExpendable extends Item implements Serializable {

    public SemiExpendable(){
        setItemType(ItemType.SEMI_EXPENDABLE);
    }

    public void setFunctionType(FunctionType functionType) {
        this.functionType = functionType;
    }

    public void setItemDetails(List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
