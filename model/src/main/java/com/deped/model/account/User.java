package com.deped.model.account;

import com.deped.model.borrow.BorrowItem;
import com.deped.model.location.City;
import com.deped.model.location.office.Section;
import com.deped.model.security.Role;
import com.deped.protection.validators.date.Age;
import com.deped.protection.validators.date.DateRange;
import com.deped.protection.validators.fieldmatcher.FieldMatch;
import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.deped.repository.utils.ConstantValues.*;

/**
 * Created by Mehdi on 6/8/2017.
 */

@NamedQueries({
        @NamedQuery(
                name = FIND_BY_USERNAME,
                query = "SELECT user From User user WHERE user.username=:username AND user.password=:password"
        ),
        @NamedQuery(
                name = FIND_BY_EMAIL,
                query = "SELECT user From User user WHERE user.emailAddress=:emailAddress AND user.password=:password"
        ), @NamedQuery(
        name = FETCH_ALL_USERS,
        query = "SELECT user FROM User user"
), @NamedQuery(
        name = "fetchUser",
        query = "SELECT user FROM User user WHERE user.username = :username"
), @NamedQuery(
        name = "fetchUserByEmail",
        query = "SELECT user FROM User user WHERE user.emailAddress = :emailAddress"
)

//        @NamedQuery(
//                name = "fetchAllByFilter",
//                query = "SELECT user From User user WHERE " +
//                        "(user.username LIKE :username OR :username IS NULL) AND " +
//                        "(user.accountStatus = :accountStatus OR :accountStatus IS NULL) AND " +
//                        "(user.firstName LIKE :firstName OR :firstName IS NULL) AND " +
//                        "(user.lastName LIKE :lastName OR :lastName IS NULL) AND " +
//                        "(user.middle_name LIKE :middleName OR :middleName IS NULL) AND " +
//                        "(user.emailAddress LIKE :emailAddress OR :emailAddress IS NULL) AND " +
//                        "(user.gender = :gender OR :gender IS NULL) AND " +
//                        "((user.employmentDate > :employmentFromDate AND user.employmentDate < :employmentToDate ) OR :employmentFromDate IS NULL OR :employmentToDate IS NULL) AND " +
//                        "(user.position = :position OR :position IS NULL) AND " +
//                        "(user.section = :section OR :section IS NULL)"
//        ),

//        ),
//        @NamedQuery(
//                name="deleteUserById",
//                query = "DELETE FROM User user WHERE user.getUserId = :userId"
//        )
})

@Entity
@Table(name = "user")
@FieldMatch.List({
        @FieldMatch(first = "password", second = "repassword", message = "The password fields must match"),
})
public class User implements Serializable {


    @NotEmpty(message = "Username field can not be blank")
    @Length(min = 3, max = 45, message = "Username field must be between 3 to 45 character")
    @XSS
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Name field must contain number, underscore and alphabet only")
    @Column(name = "username")
    @Id
    private String username;

    @Transient
    private String previousIdUsername;


    @NotEmpty(message = "Password field can not be blank")
    @Length(min = 3, max = 45, message = "Password field must be between 3 to 45 character")
    @Column(name = "password")
    private String password;

    @Transient
    @NotEmpty(message = "Repeat password field can not be blank")
    @Length(min = 3, max = 45, message = "Repeat password field must be between 3 to 45 character")
    private String repassword;

    @Column(name = "account_status")
    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "first_name")
    @NotEmpty(message = "First name field can not be blank")
    @Length(min = 2, max = 45, message = "First name field must be between 2 to 45 character")
    @XSS
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name field can not be blank")
    @Length(min = 2, max = 45, message = "Last name field must be between 2 to 45 character")
    @XSS
    private String lastName;

    @Column(name = "middle_name")
    @Length(min = 0, max = 45, message = "Middle name field can't be more than 45 character")
    @XSS
    private String middleName;

    @Column(name = "email_address", unique = true)
    @NotEmpty(message = "Email address is blank")
//    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Please provide correct email address")
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is wrong")
    @Length(min = 5, max = 100, message = "Email address must be between 5 to 100 character")
    private String emailAddress;

    @Column(name = "phone_no1")
    @NotEmpty(message = "Phone number 1 field can not be blank")
    @Length(min = 5, max = 45, message = "Phone number 1 field must be between 5 to 45 character")
    private String phoneNo1;

    @Column(name = "phone_no2")
    @Length(min = 5, max = 45, message = "Phone number 2 field must be between 5 to 45 character")
    private String phoneNo2;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Gender field can not be blank")
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    @Age(age = 140, required = true, message = "your input for birth date is out of the scope")
    @NotNull(message = "this messa")
    private Date birthDate;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "address")
    @NotEmpty(message = "Address field can not be blank!")
    @Length(max = 400, message = "Address field can't be more than 400 character")
    @XSS
    private String address;

    @Column(name = "website")
    @Length(max = 100, message = "your website field can not be more than 100 character")
//    @Pattern(regexp = "^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$")
    @URL(regexp = "^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$", message = "Your website url is wrong")
    private String website;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @Column(name = "referrer_name")
    @Length(min = 2, max = 100, message = "Referrer name field must be between 2 to 45 character")
    @NotEmpty(message = "Referrer name can not be blank")
    @XSS
    private String referrerName;

    @Column(name = "referrer_address")
    @Length(min = 2, max = 400, message = "Referrer address field field must be between 2 to 400 character")
    @XSS
    @NotEmpty(message = "Referrer address can not be blank")
    private String referrerAddress;

    @Column(name = "referrer_phone_no1")
    @Length(min = 5, max = 45, message = "Referrer phone number field must be between 5 to 45 character")
    @NotNull(message = "First referrer phone number name can not be blank!")
    //@Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Referrer phone number 1 field must only contain number")
    private String referrerPhoneNo1;

    @Column(name = "referrer_phone_no2")
    @Length(min = 5, max = 45, message = "Referrer phone number field must be between 5 to 45 character")
    //@Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Referrer phone number 2 field must only contain number")
    private String referrerPhoneNo2;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "section_name")
    @NotNull(message = "Section can not be null")
    private Section section;

//    @Column(name = "manager_id")
//    private User manager;

    //    @Formula("lower(datediff(curdate(), birth_date)/365)")
    @Transient
    private Integer age;

    @Temporal(TemporalType.DATE)
    @Column(name = "employment_date")
    @DateRange(required = false, yearFrom = 1901, monthFrom = 1, dayFrom = 21, message = "your employment date must be between 1/21/1901 up to now")
    private Date employmentDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "city_of_born")
    private City cityOfBorn;

    @Column(name = "pic_url")
    private String picUrl;

    @Transient
    private String pictureBase64;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "username"/*, referencedColumnName = "user_id"*/),
            inverseJoinColumns = @JoinColumn(
                    name = "role_name"/*, referencedColumnName = "role_id"*/))
    private Set<Role> roles;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<BorrowItem> borrowList = new HashSet<>();

    public User() {
    }

    public User(String username, String password, AccountStatus accountStatus, String firstName, String lastName, String middleName, String emailAddress, String phoneNo1, String phoneNo2, Gender gender, Date birthDate, Date employmentDate, Position position, String address, String website, Date creationDate, String referrerName, String referrerAddress, String referrerPhoneNo1, String referrerPhoneNo2, Section section, Integer age) {
        this.username = username;
        this.password = password;
        this.accountStatus = accountStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.emailAddress = emailAddress;
        this.phoneNo1 = phoneNo1;
        this.phoneNo2 = phoneNo2;
        this.gender = gender;
        this.birthDate = birthDate;
        this.employmentDate = employmentDate;
        this.position = position;
        this.address = address;
        this.website = website;
        this.creationDate = creationDate;
        this.referrerName = referrerName;
        this.referrerAddress = referrerAddress;
        this.referrerPhoneNo1 = referrerPhoneNo1;
        this.referrerPhoneNo2 = referrerPhoneNo2;
        this.section = section;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public void setPhoneNo1(String phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerAddress() {
        return referrerAddress;
    }

    public void setReferrerAddress(String referrerAddress) {
        this.referrerAddress = referrerAddress;
    }

    public String getReferrerPhoneNo1() {
        return referrerPhoneNo1;
    }

    public void setReferrerPhoneNo1(String referrerPhoneNo1) {
        this.referrerPhoneNo1 = referrerPhoneNo1;
    }

    public String getReferrerPhoneNo2() {
        return referrerPhoneNo2;
    }

    public void setReferrerPhoneNo2(String referrerPhoneNo2) {
        this.referrerPhoneNo2 = referrerPhoneNo2;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public City getCityOfBorn() {
        return cityOfBorn;
    }

    public void setCityOfBorn(City cityOfBorn) {
        this.cityOfBorn = cityOfBorn;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPreviousIdUsername() {
        return previousIdUsername;
    }

    public void setPreviousIdUsername(String previousIdUsername) {
        this.previousIdUsername = previousIdUsername;
    }

    public Set<BorrowItem> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(Set<BorrowItem> borrowList) {
        this.borrowList = borrowList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return username != null ? username.equals(user.getUsername()) : user.getUsername() == null;
    }

    @Override
    public int hashCode() {
        if (username == null || username.isEmpty())
            return super.hashCode();
        else
            return username.hashCode();
    }
}