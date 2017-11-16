package com.deped.model.pack;

import com.deped.protection.validators.xss.XSS;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_PACKS;

/**
 * Created by mehdi on 7/6/17.
 */

@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_PACKS,
                query = "SELECT p FROM Pack p"
        )
})
@Entity
@Table(name = "pack")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "packId", scope = Serializable.class)
public class Pack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_id")
    private Long packId;

    @Column(name = "name")
    @NotEmpty(message = "Name field can not be blank")
    @Length(min = 2, max = 45, message = "Name filed length must be between 2 to 45 character")
    @XSS
    private String name;

    @Column(name = "description")
    @Length(max = 400, message = "Description field must not be more than 400 character")
    @XSS
    private String description;

    @Column(name = "capacity")
    @Min(value = 1, message = "Package capacity can not be a negative")
    @Max(value = 5000, message = "Package capacity can not be more than 5000")
    private Integer capacity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pack")
//    private List<OrderDetails> orderDetailsList;


    public Pack() {
    }

    public Pack(String name, String description, Date creationDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pack)) return false;

        Pack pack = (Pack) o;

        return packId.equals(pack.packId);
    }

    @Override
    public int hashCode() {
        if (packId == null || packId == 0L)
            return super.hashCode();
        else
            return packId.hashCode();
    }
}
