package com.deped.model.location;

import javax.persistence.*;

@Table(name = "city")
@Entity
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

    @ManyToOne
    @JoinColumn(name = "country_code")
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
