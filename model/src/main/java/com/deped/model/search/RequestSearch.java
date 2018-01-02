package com.deped.model.search;

import com.deped.model.account.User;
import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.request.RequestStatus;
import com.deped.model.tracker.RequestTracker;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class RequestSearch {

    private String userMessage;

    private Date requestDateFrom;

    private Date requestDateTo;

    private List<User> requestedUsers;

    private String adminNotice;

    private ItemType itemType;

    private List<Item> items;

    private Integer requestQuantityFrom;

    private Integer requestQuantityTo;

    private Integer approvedQuantityFrom;

    private Integer approvedQuantityTo;

    private Date approvalDisapprovalDateFrom;

    private Date approvalDisapprovalDateTo;

    private String disapprovalMessage;

    private Date releaseDateFrom;

    private Date releaseDateTo;

    private RequestStatus requestStatus;

    private RequestDetailsStatus requestDetailsStatus;

    private String cancellationReason;

    private String supplyOfficeRemark;

    private List<User> consideredByUsers;

    private List<User> issuedByUsers;

    private Set<RequestTracker> requestTrackers;

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getRequestDateFrom() {
        return requestDateFrom;
    }

    public void setRequestDateFrom(Date requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    public Date getRequestDateTo() {
        return requestDateTo;
    }

    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    public List<User> getRequestedUsers() {
        return requestedUsers;
    }

    public void setRequestedUsers(List<User> requestedUsers) {
        this.requestedUsers = requestedUsers;
    }

    public String getAdminNotice() {
        return adminNotice;
    }

    public void setAdminNotice(String adminNotice) {
        this.adminNotice = adminNotice;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getRequestQuantityFrom() {
        return requestQuantityFrom;
    }

    public void setRequestQuantityFrom(Integer requestQuantityFrom) {
        this.requestQuantityFrom = requestQuantityFrom;
    }

    public Integer getRequestQuantityTo() {
        return requestQuantityTo;
    }

    public void setRequestQuantityTo(Integer requestQuantityTo) {
        this.requestQuantityTo = requestQuantityTo;
    }

    public Integer getApprovedQuantityFrom() {
        return approvedQuantityFrom;
    }

    public void setApprovedQuantityFrom(Integer approvedQuantityFrom) {
        this.approvedQuantityFrom = approvedQuantityFrom;
    }

    public Integer getApprovedQuantityTo() {
        return approvedQuantityTo;
    }

    public void setApprovedQuantityTo(Integer approvedQuantityTo) {
        this.approvedQuantityTo = approvedQuantityTo;
    }

    public Date getApprovalDisapprovalDateFrom() {
        return approvalDisapprovalDateFrom;
    }

    public void setApprovalDisapprovalDateFrom(Date approvalDisapprovalDateFrom) {
        this.approvalDisapprovalDateFrom = approvalDisapprovalDateFrom;
    }

    public Date getApprovalDisapprovalDateTo() {
        return approvalDisapprovalDateTo;
    }

    public void setApprovalDisapprovalDateTo(Date approvalDisapprovalDateTo) {
        this.approvalDisapprovalDateTo = approvalDisapprovalDateTo;
    }

    public String getDisapprovalMessage() {
        return disapprovalMessage;
    }

    public void setDisapprovalMessage(String disapprovalMessage) {
        this.disapprovalMessage = disapprovalMessage;
    }

    public Date getReleaseDateFrom() {
        return releaseDateFrom;
    }

    public void setReleaseDateFrom(Date releaseDateFrom) {
        this.releaseDateFrom = releaseDateFrom;
    }

    public Date getReleaseDateTo() {
        return releaseDateTo;
    }

    public void setReleaseDateTo(Date releaseDateTo) {
        this.releaseDateTo = releaseDateTo;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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

    public String getSupplyOfficeRemark() {
        return supplyOfficeRemark;
    }

    public void setSupplyOfficeRemark(String supplyOfficeRemark) {
        this.supplyOfficeRemark = supplyOfficeRemark;
    }

    public List<User> getConsideredByUsers() {
        return consideredByUsers;
    }

    public void setConsideredByUsers(List<User> consideredByUsers) {
        this.consideredByUsers = consideredByUsers;
    }

    public List<User> getIssuedByUsers() {
        return issuedByUsers;
    }

    public void setIssuedByUsers(List<User> issuedByUsers) {
        this.issuedByUsers = issuedByUsers;
    }

    public Set<RequestTracker> getRequestTrackers() {
        return requestTrackers;
    }

    public void setRequestTrackers(Set<RequestTracker> requestTrackers) {
        this.requestTrackers = requestTrackers;
    }
}
