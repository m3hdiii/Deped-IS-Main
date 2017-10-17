package com.deped.model.supply;

import com.deped.model.items.Item;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by mehdi on 7/6/17.
 */

@Entity
@Table(name = "supplier")
public class Supplier {

    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long supplierId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "contact_no1")
    private String supplierContactNo1;

    @Column(name = "contact_no2")
    private String supplierContactNo2;

    @Column(name = "address")
    private String supplierAddress;

    @Column(name = "remarks")
    private String remarks;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "pic_url")
    private String picUrl;

    @Transient
    private byte[] picture;

    @ManyToMany
    private Set<Item> items;


}
