package com.deped.model.location.office;

import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mehdi on 6/8/2017.
 */

@NamedQueries({
        @NamedQuery(name = "fetchAllSections", query = "SELECT sec FROM Section sec"),
        @NamedQuery(name = "deleteSectionById", query = "DELETE FROM Section sec WHERE sec.sectionId = :sectionId")
})
@Entity
@Table(name = "section")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "sectionId", scope = Section.class)
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "name")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @NotEmpty(message = "Name field can not be blank")
    @XSS
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Name field must contain number and alphabet only")
    private String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description must not be more than 400 character")
    @XSS
    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    public Section() {
    }

    public Section(String name, String description, Department department, Date creationDate) {
        this.name = name;
        this.description = description;
        this.department = department;
        this.creationDate = creationDate;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}