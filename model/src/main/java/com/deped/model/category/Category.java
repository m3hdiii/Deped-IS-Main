package com.deped.model.category;

import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_CATEGORY;

/**
 * Created by mehdi on 7/6/17.
 */

@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_CATEGORY,
                query = "SELECT c FROM Category c"
        ),
})
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Column(name = "category_name")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @NotEmpty(message = "Name field can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "Name field must contain number, alphabet, space and underscore only")
    @XSS
    @Id
    private String name;

    @Transient
    private String previousIdName;

    @Column(name = "description")
    @Length(max = 400, message = "Description must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "creation_date")
    private Date creationDate;

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

    public String getPreviousIdName() {
        return previousIdName;
    }

    public void setPreviousIdName(String previousIdName) {
        this.previousIdName = previousIdName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        if (name != null)
            return name.hashCode();
        else
            return super.hashCode();
    }
}
