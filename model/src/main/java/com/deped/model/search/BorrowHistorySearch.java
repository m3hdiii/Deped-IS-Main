package com.deped.model.search;

import java.util.Date;

public class BorrowHistorySearch {

    private String username;
    private String officeSerialNo;
    private Date borrowDateFrom;
    private Date borrowDateTo;
    private Date returnDateFrom;
    private Date returnDateTo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOfficeSerialNo() {
        return officeSerialNo;
    }

    public void setOfficeSerialNo(String officeSerialNo) {
        this.officeSerialNo = officeSerialNo;
    }

    public Date getBorrowDateFrom() {
        return borrowDateFrom;
    }

    public void setBorrowDateFrom(Date borrowDateFrom) {
        this.borrowDateFrom = borrowDateFrom;
    }

    public Date getBorrowDateTo() {
        return borrowDateTo;
    }

    public void setBorrowDateTo(Date borrowDateTo) {
        this.borrowDateTo = borrowDateTo;
    }

    public Date getReturnDateFrom() {
        return returnDateFrom;
    }

    public void setReturnDateFrom(Date returnDateFrom) {
        this.returnDateFrom = returnDateFrom;
    }

    public Date getReturnDateTo() {
        return returnDateTo;
    }

    public void setReturnDateTo(Date returnDateTo) {
        this.returnDateTo = returnDateTo;
    }
}
