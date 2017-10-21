package com.deped.model.location;

import static com.deped.repository.utils.ConstantValues.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

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
