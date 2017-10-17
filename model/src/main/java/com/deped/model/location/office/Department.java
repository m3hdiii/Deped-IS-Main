package com.deped.model.location.office;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_DEPARTMENTS;


/**
 * Created by Mehdi on 6/8/2017.
 */


@NamedQueries({
        @NamedQuery(name = FETCH_ALL_DEPARTMENTS, query = "SELECT dep FROM Department dep"),
        @NamedQuery(name = "fetchDepartments", query = "SELECT dep FROM Department dep")
})
@Entity
@Table(name = "department")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "departmentId", scope = Department.class)
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "department_head")
    private String departmentHead;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    @JsonBackReference("department-binding")
    private List<Section> sections = new ArrayList<>();


    public Department() {
    }

    public Department(String name, String description, List<Section> sections, Date creationDate, String departmentHead) {
        this.name = name;
        this.description = description;
        this.sections = sections;
        this.creationDate = creationDate;
        this.departmentHead = departmentHead;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }


}
