package com.deped.model.brand;

import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_BRANDS;
import static com.deped.repository.utils.ConstantValues.FETCH_ALL_BRAND_RANGES;

/**
 * Created by mehdi on 7/6/17.
 */

@NamedQueries({
        @NamedQuery(name = FETCH_ALL_BRANDS, query = "SELECT b FROM Brand b"),
        @NamedQuery(name = FETCH_ALL_BRAND_RANGES, query = "SELECT b FROM Brand b")
})
@Entity
@Table(name = "brand")
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "name")
    @NotEmpty(message = "Name field can not be blank")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @XSS
    @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "Name field must contain number, alphabet, space and underscore only")
    private String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modelNumber")
    private List<BrandModel> brandModels;

    @Column(name = "logo_url")
    private String logoUrl;

    @Transient
    private String logoPic;

//    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "brands")
//    private Set<Item> items;

    public Brand() {
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public List<BrandModel> getBrandModels() {
        return brandModels;
    }

    public void setBrandModels(List<BrandModel> brandModels) {
        this.brandModels = brandModels;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    //    public Set<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(Set<Item> items) {
//        this.items = items;
//    }
}
