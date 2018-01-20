package com.deped.model.borrow;

import com.deped.model.account.User;
import com.deped.model.items.ItemDetails;
import com.deped.protection.validators.date.Age;
import com.deped.protection.validators.date.DateRange;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "borrow_item")
public class BorrowItem implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "borrow_item_id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_office_serial_no")
    private ItemDetails itemDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_borrowed", nullable = false)
    @DateRange(yearFrom = 1901, monthFrom = 1, dayFrom = 21, message = "your borrow date must be between 1/21/1901 up to now")
    private Date borrowDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_return")
    @DateRange(yearFrom = 1901, monthFrom = 1, dayFrom = 21, message = "your return date must be between 1/21/1901 up to now")
    private Date returnDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
        this.itemDetails = itemDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
