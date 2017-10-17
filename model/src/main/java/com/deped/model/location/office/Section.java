package com.deped.model.location.office;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "sectionId", scope = Section.class)
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id")
//    @JsonManagedReference("department-binding")
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