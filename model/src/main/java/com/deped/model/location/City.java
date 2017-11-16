package com.deped.model.location;

import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_CITIES_BY_COUNTRY_CODE;

@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_CITIES_BY_COUNTRY_CODE,
                query = "SELECT c FROM City c WHERE c.country.countryCode = :countryCode"
        )
})
@Table(name = "city")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cityId", scope = City.class)
public class City {

    @Column(name = "city_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(name = "name")
    @Length(min = 2, max = 45, message = "name must be between 2 to 45 character character")
    @NotEmpty(message = "Name field can not be blank")
    @XSS
    private String name;

    @Column(name = "district")
    private String district;

    @Column(name = "population")
    private Long population;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    @JsonBackReference("city-binding")
    private Country country;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
