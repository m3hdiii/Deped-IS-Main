package com.deped.model.request;


import com.deped.model.account.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "requestId", scope = Request.class)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "user_message")
    private String userMessage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date")
    private Date requestDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "request", cascade=CascadeType.ALL)
    @JsonBackReference("requestDetails-binding")
    private Set<RequestDetails> requestDetails;

    @Column(name = "admin_notice")
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

    public Set<RequestDetails> getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(Set<RequestDetails> requestDetails) {
        this.requestDetails = requestDetails;
    }

    public String getAdminNotice() {
        return adminNotice;
    }

    public void setAdminNotice(String adminNotice) {
        this.adminNotice = adminNotice;
    }
}