package com.deped.model.location.office;

import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
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
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "departmentId", scope = Department.class)
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @NotEmpty(message = "Name field can not be blank")
    @XSS
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "Name field must contain number, alphabet, space and underscore only")
    private String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "department_head")
    @Length(min = 2, max = 45, message = "Department head filed length must be between 2 to 45 character")
    @XSS
    private String departmentHead;


    public Department() {
    }

    public Department(String name, String description, List<Section> sections, Date creationDate, String departmentHead) {
        this.name = name;
        this.description = description;
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
