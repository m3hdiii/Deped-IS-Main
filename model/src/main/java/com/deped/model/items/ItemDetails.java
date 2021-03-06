package com.deped.model.items;

import com.deped.model.account.User;
import com.deped.model.borrow.BorrowItem;
import com.deped.model.brand.BrandModel;
import com.deped.model.items.features.Colour;
import com.deped.model.items.features.Condition;
import com.deped.model.items.features.EquipmentAvailability;
import com.deped.model.items.features.Material;
import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "item_details")
@NamedQueries({
        @NamedQuery(
                name = "getAll",
                query = "SELECT id FROM ItemDetails id ORDER BY id.creationDate"
        )
})
public class
ItemDetails implements Serializable {


    @NotEmpty(message = "Office serial number field can not be blank")
    @Length(min = 1, max = 25, message = "Office serial number filed length must be between 1 to 45 character")
    @XSS
    @Id
    @Column(name = "office_serial_number")
    private String officeSerialNo;

    @Column(name = "colour")
    @Enumerated(EnumType.STRING)
    private Colour colour;

    @Column(name = "equipment_condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "equipment_availability")
    @Enumerated(EnumType.STRING)
    private EquipmentAvailability equipmentAvailability;


    @Column(name = "equipment_serial_number")
    @Length(max = 45, message = "Equipment serial number filed length must less than 45 character")
    @XSS
    private String equipmentSerialNo;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "material")
    private Material material;

    @Column(name = "weight_in_gram")
    @Min(value = 1, message = "Weight field must be at least one gram")
    private Long weightInGram;

    @Column(name = "life_span")
    @Min(value = 1, message = "Life span field must be at least one month")
    private Short lifeSpan;

    @ManyToOne
    @JoinColumn(name = "item_name")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "brand_model_id")
    private BrandModel brandModel;

    @OneToOne
    @JoinColumn(name = "owns_by")
    private User ownBy;

    @Column(name = "pic_url")
    private String picUrl;

    @OneToMany(mappedBy = "itemDetails", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BorrowItem> borrowItems;

    public ItemDetails() {
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public EquipmentAvailability getEquipmentAvailability() {
        return equipmentAvailability;
    }

    public void setEquipmentAvailability(EquipmentAvailability equipmentAvailability) {
        this.equipmentAvailability = equipmentAvailability;
    }

    public String getOfficeSerialNo() {
        return officeSerialNo;
    }

    public void setOfficeSerialNo(String officeSerialNo) {
        this.officeSerialNo = officeSerialNo;
    }

    public String getEquipmentSerialNo() {
        return equipmentSerialNo;
    }

    public void setEquipmentSerialNo(String equipmentSerialNo) {
        this.equipmentSerialNo = equipmentSerialNo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Long getWeightInGram() {
        return weightInGram;
    }

    public void setWeightInGram(Long weightInGram) {
        this.weightInGram = weightInGram;
    }

    public Short getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(Short lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BrandModel getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(BrandModel brandModel) {
        this.brandModel = brandModel;
    }

    public User getOwnBy() {
        return ownBy;
    }

    public void setOwnBy(User ownBy) {
        this.ownBy = ownBy;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<BorrowItem> getBorrowItems() {
        return borrowItems;
    }

    public void setBorrowItems(List<BorrowItem> borrowItems) {
        this.borrowItems = borrowItems;
    }
}
