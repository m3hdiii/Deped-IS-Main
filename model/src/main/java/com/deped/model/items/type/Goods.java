package com.deped.model.items.type;

import com.deped.model.items.Item;
import com.deped.model.items.ItemType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("GOODS")
public class Goods extends Item implements Serializable {

    public Goods(){
        setItemType(ItemType.GOODS);
    }
}
