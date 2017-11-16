package com.deped.model.brand;

import com.deped.model.items.ItemDetails;
import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "brand_model")
public class BrandModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_model_id")
    private Long brandModelId;

    @Column(name = "model_number")
    @NotEmpty(message = "Model number field can not be blank")
    @Length(min = 1, max = 45, message = "Model number field must be between 2 to 45 character")
    @XSS
    private String modelNumber;

    @Column(name = "description")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "specification")
    @Length(max = 400, message = "Specification field must not be more than 400 character")
    private String specification;

    @Column(name = "pic_url")
    private String picUrl;

    @Transient
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandModel")
    private List<ItemDetails> itemDetails;


    public Long getBrandModelId() {
        return brandModelId;
    }

    public void setBrandModelId(Long brandModelId) {
        this.brandModelId = brandModelId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<ItemDetails> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
