package com.deped.model.request;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RequestDetailsID implements Serializable {

    @Column(name = "request")
    private Long requestId;

    @Column(name = "item")
    private String itemName;

    public RequestDetailsID(Long requestId, String itemName) {
        this.requestId = requestId;
        this.itemName = itemName;
    }

    public RequestDetailsID() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
