package com.deped.model.search;

import com.deped.model.account.User;
import com.deped.model.brand.BrandModel;
import com.deped.model.items.Item;
import com.deped.model.items.features.Colour;
import com.deped.model.items.features.Condition;
import com.deped.model.items.features.EquipmentAvailability;
import com.deped.model.items.features.Material;

import java.util.Date;
import java.util.List;

public class BorrowSearch {

    private String officeSerialNo;

    private List<Colour> colours;

    private List<Condition> conditions;

    private Double purchasePriceFrom;

    private Double purchasePriceTo;

    private List<EquipmentAvailability> equipmentAvailabilities;

    private String equipmentSerialNo;

    private Date creationDateFrom;

    private Date creationDateTo;

    private List<Material> materials;

    private Long weightInGramFrom;

    private Long weightInGramTo;

    private Short lifeSpanFrom;

    private Short lifeSpanTo;

    private List<Item> items;

    private List<BrandModel> brandModels;

    private List<User> ownBy;

    public String getOfficeSerialNo() {
        return officeSerialNo;
    }

    public void setOfficeSerialNo(String officeSerialNo) {
        this.officeSerialNo = officeSerialNo;
    }

    public List<Colour> getColours() {
        return colours;
    }

    public void setColours(List<Colour> colours) {
        this.colours = colours;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Double getPurchasePriceFrom() {
        return purchasePriceFrom;
    }

    public void setPurchasePriceFrom(Double purchasePriceFrom) {
        this.purchasePriceFrom = purchasePriceFrom;
    }

    public Double getPurchasePriceTo() {
        return purchasePriceTo;
    }

    public void setPurchasePriceTo(Double purchasePriceTo) {
        this.purchasePriceTo = purchasePriceTo;
    }

    public List<EquipmentAvailability> getEquipmentAvailabilities() {
        return equipmentAvailabilities;
    }

    public void setEquipmentAvailabilities(List<EquipmentAvailability> equipmentAvailabilities) {
        this.equipmentAvailabilities = equipmentAvailabilities;
    }

    public String getEquipmentSerialNo() {
        return equipmentSerialNo;
    }

    public void setEquipmentSerialNo(String equipmentSerialNo) {
        this.equipmentSerialNo = equipmentSerialNo;
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public Long getWeightInGramFrom() {
        return weightInGramFrom;
    }

    public void setWeightInGramFrom(Long weightInGramFrom) {
        this.weightInGramFrom = weightInGramFrom;
    }

    public Long getWeightInGramTo() {
        return weightInGramTo;
    }

    public void setWeightInGramTo(Long weightInGramTo) {
        this.weightInGramTo = weightInGramTo;
    }

    public Short getLifeSpanFrom() {
        return lifeSpanFrom;
    }

    public void setLifeSpanFrom(Short lifeSpanFrom) {
        this.lifeSpanFrom = lifeSpanFrom;
    }

    public Short getLifeSpanTo() {
        return lifeSpanTo;
    }

    public void setLifeSpanTo(Short lifeSpanTo) {
        this.lifeSpanTo = lifeSpanTo;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<BrandModel> getBrandModels() {
        return brandModels;
    }

    public void setBrandModels(List<BrandModel> brandModels) {
        this.brandModels = brandModels;
    }

    public List<User> getOwnBy() {
        return ownBy;
    }

    public void setOwnBy(List<User> ownBy) {
        this.ownBy = ownBy;
    }
}
