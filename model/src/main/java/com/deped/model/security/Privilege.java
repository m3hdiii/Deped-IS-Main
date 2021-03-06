package com.deped.model.security;

import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "privilege")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "privilegeId", scope = Privilege.class)
public class Privilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Long PrivilegeId;

    @Column(name = "name")
    @NotEmpty(message = "Name field can not be blank")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @XSS
    private String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    //    @ManyToMany(mappedBy = "privileges", cascade = CascadeType.ALL)
//    @JsonBackReference
    @Transient
    private Set<Role> roles;

    public Long getPrivilegeId() {
        return PrivilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        PrivilegeId = privilegeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}