package com.deped.model.tracker;

import javax.persistence.Embeddable;
import java.io.Serializable;

public class RequestTrackerID implements Serializable{

    private Long itemDetailsId;
    private Long requestId;
    private Long itemId;


    public RequestTrackerID() {
        System.out.println();
    }

    public RequestTrackerID(Long itemDetailsId, Long requestId, Long itemId) {
        this.itemDetailsId = itemDetailsId;
        this.requestId = requestId;
        this.itemId = itemId;
    }

    public Long getItemDetailsId() {
        return itemDetailsId;
    }

    public void setItemDetailsId(Long itemDetailsId) {
        this.itemDetailsId = itemDetailsId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
