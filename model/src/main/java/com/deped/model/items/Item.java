package com.deped.model.items;


import com.deped.model.items.features.FunctionType;
import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_ITEMS;
import static com.deped.repository.utils.ConstantValues.FETCH_ALL_ITEMS_BY_TYPE;


/**
 * Created by mehdi on 7/6/17.
 */

@NamedQueries({
        @NamedQuery(name = FETCH_ALL_ITEMS, query = "SELECT i FROM Item i"),
        @NamedQuery(name = FETCH_ALL_ITEMS_BY_TYPE, query = "SELECT i FROM Item i WHERE i.itemType  = :itemType")
})
@Entity
@Table(name = "item")
@DynamicUpdate
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "name")
    @Length(min = 2, max = 45, message = "name must be between 2 to 45 character character")
    @NotEmpty(message = "Name field can not be blank")
    @XSS
    protected String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "item_type")
    @Enumerated(value = EnumType.STRING)
    @NotEmpty(message = "Item Type field can not be blank")
    private ItemType itemType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "function_type")
    @NotEmpty(message = "Function type field can not be blank")
    protected FunctionType functionType;

    @Column(name = "threshold")
    @NotEmpty(message = "threshold type can not be blank")
    @Min(value = 0, message = "Threshold field must not be negative")
    private Integer threshold;

    @Column(name = "quantity")
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "pic_name")
    private String picName;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    @JsonIgnore
    protected List<ItemDetails> itemDetails;

    @Transient
    private String pictureBase64;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "item")
//    private Set<OrderDetails> orderItems;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.item", cascade=CascadeType.ALL)
//    private Set<RequestItem> requestItems;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "item_all",
//            joinColumns=@JoinColumn(name = "item_id"),
//            inverseJoinColumns = @JoinColumn(name = "brand_id")
//    )
    //    private Set<Brand> brands;

//TODO
//    @ManyToMany
//    private Set<Supplier> suppliers;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public FunctionType getFunctionType() {
        return functionType;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    public void setFunctionType(FunctionType functionType) {
        this.functionType = functionType;
    }

    public List<ItemDetails> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }

//    public Set<OrderDetails> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(Set<OrderDetails> orderItems) {
//        this.orderItems = orderItems;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return itemId != null ? itemId.equals(item.itemId) : item.itemId == null;
    }

    @Override
    public int hashCode() {
        if (itemId == null || itemId == 0L)
            return super.hashCode();
        else
            return itemId.hashCode();
    }
}
