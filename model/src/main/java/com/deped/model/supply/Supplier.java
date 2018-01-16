package com.deped.model.supply;

import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_SUPPLIER;

/**
 * Created by mehdi on 7/6/17.
 */


@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_SUPPLIER,
                query = "SELECT s FROM Supplier s"
        )
})
@Entity
@Table(name = "supplier")
public class

Supplier {

    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long supplierId;

    @Column(name = "name")
    @NotEmpty(message = "Name field can not be blank")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @XSS
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "Name field must contain number, alphabet, space and underscore only")
    private String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "contact_no1")
    @NotEmpty(message = "Supplier contact no 1 field can not be blank")
    @XSS
    @Length(min = 2, max = 45, message = "Supplier contact no 1 field must be between 2 to 45 character")
//    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Supplier contact no 1 field must only contain number")
    private String supplierContactNo1;

    @Column(name = "contact_no2")
    @Length(min = 2, max = 45, message = "Supplier contact no 2 field must be between 2 to 45 character")
//    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Supplier contact no 2 field must only contain number")
    @XSS
    private String supplierContactNo2;

    @Column(name = "address")
    @Length(max = 400, message = "Supplier address field must not be more than 400 character")
    @XSS
    private String supplierAddress;

    @Column(name = "remarks")
    @Length(max = 400, message = "Supplier address field must not be more than 400 character")
    @XSS
    private String remarks;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "pic_name")
    private String picName;

    @Transient
    private String pictureBase64;
//    @ManyToMany
//    private Set<Item> items;


    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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

    public String getSupplierContactNo1() {
        return supplierContactNo1;
    }

    public void setSupplierContactNo1(String supplierContactNo1) {
        this.supplierContactNo1 = supplierContactNo1;
    }

    public String getSupplierContactNo2() {
        return supplierContactNo2;
    }

    public void setSupplierContactNo2(String supplierContactNo2) {
        this.supplierContactNo2 = supplierContactNo2;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    //    public Set<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(Set<Item> items) {
//        this.items = items;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;

        Supplier supplier = (Supplier) o;

        return supplierId.equals(supplier.supplierId);
    }

    @Override
    public int hashCode() {
        if (supplierId != null)
            return supplierId.hashCode();
        else return super.hashCode();
    }
}
