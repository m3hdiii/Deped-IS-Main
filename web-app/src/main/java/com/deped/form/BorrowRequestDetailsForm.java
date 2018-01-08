package com.deped.form;

import com.deped.model.tracker.RequestTracker;

import java.util.List;

public class BorrowRequestDetailsForm {
    private List<RequestTracker> listOfRequestTracker;

    public BorrowRequestDetailsForm() {
    }

    public BorrowRequestDetailsForm(List<RequestTracker> listOfRequestTracker) {
        this.listOfRequestTracker = listOfRequestTracker;
    }

    public List<RequestTracker> getListOfRequestTracker() {
        return listOfRequestTracker;
    }

    public void setListOfRequestTracker(List<RequestTracker> listOfRequestTracker) {
        this.listOfRequestTracker = listOfRequestTracker;
    }
}
