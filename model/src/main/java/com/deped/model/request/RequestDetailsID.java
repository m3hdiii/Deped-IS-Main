package com.deped.model.request;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RequestDetailsID implements Serializable {

    @Column(name = "request")
    private Long requestId;

    @Column(name = "item")
    private Long itemId;

    public RequestDetailsID(Long requestId, Long itemId) {
        this.requestId = requestId;
        this.itemId = itemId;
    }

    public RequestDetailsID() {
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
