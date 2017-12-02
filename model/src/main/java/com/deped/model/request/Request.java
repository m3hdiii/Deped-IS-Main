package com.deped.model.request;


import com.deped.model.account.User;
import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_REQUESTS;


/**
 * Created by mehdi on 7/6/17.
 */

@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_REQUESTS,
                query = "SELECT r FROM Request r"
        )
})
@Entity
@Table(name = "request")
@JsonSerialize
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "user_message")
    @Length(max = 400, message = "User message field must not be more than 400 character")
    @XSS
    private String userMessage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date")
    private Date requestDate;

//    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "requestDetailsRequest")
//    private Set<RequestDetails> requestDetails;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "admin_notice")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String adminNotice;


    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Set<RequestDetails> getRequestDetails() {
//        return requestDetails;
//    }
//
//    public void setRequestDetails(Set<RequestDetails> requestDetails) {
//        this.requestDetails = requestDetails;
//    }

    public String getAdminNotice() {
        return adminNotice;
    }

    public void setAdminNotice(String adminNotice) {
        this.adminNotice = adminNotice;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}