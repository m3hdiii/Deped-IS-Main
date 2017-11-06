package com.deped.model.request;

import com.deped.model.account.User;
import com.deped.model.items.Item;
import com.deped.model.tracker.RequestTracker;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "request_details")
@DynamicUpdate
public class RequestDetails implements Serializable {

    @EmbeddedId
    private RequestDetailsID requestDetailsID = new RequestDetailsID();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "request_request_id")
    @MapsId("requestId")
    private Request request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_item_id")
    @MapsId("itemId")
    private Item item;

    @Column(name = "request_quantity")
    private Integer requestQuantity;

    @Column(name = "approved_quantity")
    private Integer approvedQuantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approval_disapproval_date")
    private Date approvalDisapprovalDate;

    @Column(name = "disapproval_message")
    private String disapprovalMessage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "request_details_status")
    @Enumerated(EnumType.STRING)
    private RequestDetailsStatus requestDetailsStatus = RequestDetailsStatus.WAITING;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "supply_office_remark")
    private String supplyOfficeRemark;

    @JoinColumn(name = "approver_user_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User approverUser;

    @JoinColumn(name = "issuer_user_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User issuerUser;

    @JoinColumn(name = "receiver_user_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User receiverUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestDetails")
    private Set<RequestTracker> requestTrackers;

    public RequestDetails() {
    }

    public RequestDetailsID getRequestDetailsID() {
        return requestDetailsID;
    }

    public void setRequestDetailsID(RequestDetailsID requestDetailsID) {
        this.requestDetailsID = requestDetailsID;
    }

    public Integer getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(Integer requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public Integer getApprovedQuantity() {
        return approvedQuantity;
    }

    public void setApprovedQuantity(Integer approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    public Date getApprovalDisapprovalDate() {
        return approvalDisapprovalDate;
    }

    public void setApprovalDisapprovalDate(Date approvalDisapprovalDate) {
        this.approvalDisapprovalDate = approvalDisapprovalDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public RequestDetailsStatus getRequestDetailsStatus() {
        return requestDetailsStatus;
    }

    public void setRequestDetailsStatus(RequestDetailsStatus requestDetailsStatus) {
        this.requestDetailsStatus = requestDetailsStatus;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getDisapprovalMessage() {
        return disapprovalMessage;
    }

    public void setDisapprovalMessage(String disapprovalMessage) {
        this.disapprovalMessage = disapprovalMessage;
    }

    public String getSupplyOfficeRemark() {
        return supplyOfficeRemark;
    }

    public void setSupplyOfficeRemark(String supplyOfficeRemark) {
        this.supplyOfficeRemark = supplyOfficeRemark;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getApproverUser() {
        return approverUser;
    }

    public void setApproverUser(User approverUser) {
        this.approverUser = approverUser;
    }

    public User getIssuerUser() {
        return issuerUser;
    }

    public void setIssuerUser(User issuerUser) {
        this.issuerUser = issuerUser;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }

    public Set<RequestTracker> getRequestTrackers() {
        return requestTrackers;
    }

    public void setRequestTrackers(Set<RequestTracker> requestTrackers) {
        this.requestTrackers = requestTrackers;
    }
}
