package com.deped.model.items;

import com.deped.model.brand.BrandModel;
import com.deped.model.items.features.Colour;
import com.deped.model.items.features.Condition;
import com.deped.model.items.features.EquipmentAvailability;
import com.deped.model.items.features.Material;
import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "item_details")
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_details_id")
    private Long itemDetailsId;

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

    @Column(name = "office_serial_number")
    @NotEmpty(message = "Office serial number field can not be blank")
    @Length(min = 1, max = 45, message = "Office serial number filed length must be between 1 to 45 character")
    @XSS
    private String officeSerialNo;

    @Column(name = "equipment_serial_number")
    @NotEmpty(message = "Equipment serial number field can not be blank")
    @Length(min = 1, max = 45, message = "Equipment serial number filed length must be between 1 to 45 character")
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
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "brand_model_id")
    private BrandModel brandModel;

    @Column(name = "pic_url")
    private String picUrl;

    public Long getItemDetailsId() {
        return itemDetailsId;
    }

    public void setItemDetailsId(Long itemDetailsId) {
        this.itemDetailsId = itemDetailsId;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
