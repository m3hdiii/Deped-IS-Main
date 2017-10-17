package com.deped.model.category;

import com.deped.model.order.OrderDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_CATEGORY;

/**
 * Created by mehdi on 7/6/17.
 */

@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_CATEGORY,
                query = "SELECT c FROM Category c"
        ),
})
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
    private Set<OrderDetails> orderDetailsSet;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<OrderDetails> getOrderDetailsSet() {
        return orderDetailsSet;
    }

    public void setOrderDetailsSet(Set<OrderDetails> orderDetailsSet) {
        this.orderDetailsSet = orderDetailsSet;
    }
}
