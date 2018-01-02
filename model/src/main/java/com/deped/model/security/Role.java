package com.deped.model.security;

import com.deped.model.account.User;
import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_ROLE;

/**
 * Created by Mehdi on 6/8/2017.
 */


@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_ROLE,
                query = "SELECT r FROM Role r ORDER BY r.name")
})

@Entity
@Table(name = "role")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name", scope = Role.class)
public class Role implements Serializable {

    @Id
    @Column(name = "role_name")
    @NotEmpty(message = "Name field can not be blank")
    @Length(min = 2, max = 25, message = "Name filed length must be between 2 to 45 character")
    @XSS
    private String name;

    @Transient
    private String previousRoleIdName;

    @Column(name = "simple_name")
    @NotEmpty(message = "Simple Name field can not be blank")
    @Length(min = 2, max = 45, message = "Simple Name filed length must be between 2 to 45 character")
    @XSS
    private String simpleName;

    @Column(name = "description")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;


    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> users;

    //    @ManyToMany
//    @JoinTable(
//            name = "role_privilege",
//            joinColumns = @JoinColumn(
//                    name = "role_name", referencedColumnName = "role_name"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "privilege_id"))
//
    @Transient
    private Set<Privilege> privileges;


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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getPreviousRoleIdName() {
        return previousRoleIdName;
    }

    public void setPreviousRoleIdName(String previousRoleIdName) {
        this.previousRoleIdName = previousRoleIdName;
    }
}
