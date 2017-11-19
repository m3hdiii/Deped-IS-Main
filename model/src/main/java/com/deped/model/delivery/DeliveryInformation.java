package com.deped.model.delivery;

import com.deped.model.order.Order;
import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by mehdi on 7/6/17.
 */

@Entity
@Table(name = "delivery_info")
public class DeliveryInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryInfoId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "delivery_person_name")
    @NotEmpty(message = "Name field can not be blank")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @XSS
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "Name field must contain number, alphabet, space and underscore only")
    private String deliveryPersonName;

    @Column(name = "delivery_person_contact_no1")
    @NotEmpty(message = "Contact number 1 field can not be blank")
    @Length(min = 2, max = 45, message = "Contact number 1 field length must be between 2 to 45 character")
    @XSS
    private String deliveryPersonContactNumber;

    @Column(name = "delivery_person_contact_no2")
    @Length(min = 2, max = 45, message = "Contact number 2 field length must be between 2 to 45 character")
    @XSS
    private String deliveryPersonContactNumber2;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    public Long getDeliveryInfoId() {
        return deliveryInfoId;
    }

    public void setDeliveryInfoId(Long deliveryInfoId) {
        this.deliveryInfoId = deliveryInfoId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }

    public String getDeliveryPersonContactNumber() {
        return deliveryPersonContactNumber;
    }

    public void setDeliveryPersonContactNumber(String deliveryPersonContactNumber) {
        this.deliveryPersonContactNumber = deliveryPersonContactNumber;
    }

    public String getDeliveryPersonContactNumber2() {
        return deliveryPersonContactNumber2;
    }

    public void setDeliveryPersonContactNumber2(String deliveryPersonContactNumber2) {
        this.deliveryPersonContactNumber2 = deliveryPersonContactNumber2;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
