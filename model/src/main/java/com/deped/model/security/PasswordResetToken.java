package com.deped.model.security;

import com.deped.model.account.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "password_reset_token")
@Entity
public class PasswordResetToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "creation_date")
    private Date creationDate;

    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.DETACH)
    private User user;

    @Column(name = "token_state")
    @Enumerated(EnumType.STRING)
    private TokenState tokenState;

    public PasswordResetToken() {
    }

    public PasswordResetToken(User user, String token, Date creationDate, TokenState tokenState) {
        this.token = token;
        this.creationDate = creationDate;
        this.user = user;
        this.tokenState = tokenState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TokenState getTokenState() {
        return tokenState;
    }

    public void setTokenState(TokenState tokenState) {
        this.tokenState = tokenState;
    }
}
