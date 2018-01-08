package com.deped.model.tracker;

import com.deped.model.items.ItemDetails;
import com.deped.model.request.RequestDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "request_tracker")
public class RequestTracker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_tracker_id")
    private Long requestTrackerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "office_serial_number")
    private ItemDetails itemDetails;

    @Column(name = "release_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @Column(name = "tracking_status")
    @Enumerated(value = EnumType.STRING)
    private TrackingStatus trackingStatus;


    @Transient
    private RequestDetails requestDetails;

    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "acquired_user")
    private String acquiredUser;

    @Transient
    private List<ItemDetails> itemDetailsList;


    public RequestTracker() {
    }

    public RequestTracker(RequestDetails requestDetails, List<ItemDetails> itemDetailsList) {
        this.requestDetails = requestDetails;
        this.itemDetailsList = itemDetailsList;
    }

    public RequestTracker(ItemDetails itemDetails, Date releaseDate, Date returnDate, TrackingStatus trackingStatus, RequestDetails requestDetails) {
        this.itemDetails = itemDetails;
        this.releaseDate = releaseDate;
        this.returnDate = returnDate;
        this.trackingStatus = trackingStatus;
        this.requestDetails = requestDetails;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

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

    public List<ItemDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }

    public String getAcquiredUser() {
        return acquiredUser;
    }

    public void setAcquiredUser(String acquiredUser) {
        this.acquiredUser = acquiredUser;
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


