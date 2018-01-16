package com.deped.model.search;

import com.deped.model.account.User;
import com.deped.model.items.Item;
import com.deped.model.items.ItemType;
import com.deped.model.location.office.Section;
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

    private List<ItemType> itemTypes;

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

    private List<RequestStatus> requestStatuses;

    private List<RequestDetailsStatus> requestDetailsStatuses;

    private String cancellationReason;

    private String supplyOfficeRemark;

    private List<User> consideredByUsers;

    private List<User> issuedByUsers;

    private Set<RequestTracker> requestTrackers;

    private List<Section> sections;

    public RequestSearch() {
    }

    public RequestSearch(String userMessage, Date requestDateFrom, Date requestDateTo, List<User> requestedUsers, String adminNotice, List<ItemType> itemTypes, List<Item> items, Integer requestQuantityFrom, Integer requestQuantityTo, Integer approvedQuantityFrom, Integer approvedQuantityTo, Date approvalDisapprovalDateFrom, Date approvalDisapprovalDateTo, String disapprovalMessage, Date releaseDateFrom, Date releaseDateTo, List<RequestStatus> requestStatuses, List<RequestDetailsStatus> requestDetailsStatuses, String cancellationReason, String supplyOfficeRemark, List<User> consideredByUsers, List<User> issuedByUsers, Set<RequestTracker> requestTrackers) {
        this.userMessage = userMessage;
        this.requestDateFrom = requestDateFrom;
        this.requestDateTo = requestDateTo;
        this.requestedUsers = requestedUsers;
        this.adminNotice = adminNotice;
        this.itemTypes = itemTypes;
        this.items = items;
        this.requestQuantityFrom = requestQuantityFrom;
        this.requestQuantityTo = requestQuantityTo;
        this.approvedQuantityFrom = approvedQuantityFrom;
        this.approvedQuantityTo = approvedQuantityTo;
        this.approvalDisapprovalDateFrom = approvalDisapprovalDateFrom;
        this.approvalDisapprovalDateTo = approvalDisapprovalDateTo;
        this.disapprovalMessage = disapprovalMessage;
        this.releaseDateFrom = releaseDateFrom;
        this.releaseDateTo = releaseDateTo;
        this.requestStatuses = requestStatuses;
        this.requestDetailsStatuses = requestDetailsStatuses;
        this.cancellationReason = cancellationReason;
        this.supplyOfficeRemark = supplyOfficeRemark;
        this.consideredByUsers = consideredByUsers;
        this.issuedByUsers = issuedByUsers;
        this.requestTrackers = requestTrackers;
    }

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

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
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

    public List<RequestStatus> getRequestStatuses() {
        return requestStatuses;
    }

    public void setRequestStatuses(List<RequestStatus> requestStatuses) {
        this.requestStatuses = requestStatuses;
    }

    public List<RequestDetailsStatus> getRequestDetailsStatuses() {
        return requestDetailsStatuses;
    }

    public void setRequestDetailsStatuses(List<RequestDetailsStatus> requestDetailsStatuses) {
        this.requestDetailsStatuses = requestDetailsStatuses;
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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
