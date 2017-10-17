package com.deped.model.request;


import com.deped.model.account.User;
import com.deped.model.location.office.Section;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * Created by mehdi on 7/6/17.
 */

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
    private Set<RequestDetails> requestingUsers;


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

    public Set<RequestDetails> getRequestingUsers() {
        return requestingUsers;
    }

    public void setRequestingUsers(Set<RequestDetails> requestingUsers) {
        this.requestingUsers = requestingUsers;
    }
}