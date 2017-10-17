package com.deped.model.tracker;

import com.deped.model.items.ItemDetails;
import com.deped.model.request.RequestDetails;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "request_tracker")
public class RequestTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_tracker_id")
    private Long requestTrackerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_details_id")
    private ItemDetails itemDetails;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "tracking_status")
    @Enumerated(value = EnumType.STRING)
    private TrackingStatus trackingStatus;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "request_id"),
            @JoinColumn(name = "item_id")
    })
    private RequestDetails requestDetails;

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public TrackingStatus getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(TrackingStatus trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public RequestDetails getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(RequestDetails requestDetails) {
        this.requestDetails = requestDetails;
    }

    public Long getRequestTrackerId() {
        return requestTrackerId;
    }

    public void setRequestTrackerId(Long requestTrackerId) {
        this.requestTrackerId = requestTrackerId;
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
        this.itemDetails = itemDetails;
    }
}


