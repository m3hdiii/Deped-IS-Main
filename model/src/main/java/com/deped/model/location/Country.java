package com.deped.model.location;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "country")
public class Country {

    @Column(name = "country_code")
    @Id
    private String countryCode;

    @Column(name = "name")
    private String name;

    @Column(name = "continent")
    private Continent continent;

    @Column(name = "region")
    private String region;

    @Column(name = "surface_area")
    private double surfaceArea;

    @Column(name = "indep_year")
    private Short independentYear;

    @Column(name = "population")
    private Long population;

    @Column(name = "life_expectancy")
    private double lifeExpectancy;

    @Column(name = "gnp")
    private double gdp;

    @Column(name = "gnp_old")
    private double oldGdp;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "government_form")
    private String governmentForm;

    @Column(name = "head_of_state")
    private String headOfState;

    @JoinColumn(name = "capital")
    @OneToOne(cascade = CascadeType.ALL)
    private City capital;

    @Column(name = "code2")
    private String code2;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<City> cities;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Short getIndependentYear() {
        return independentYear;
    }

    public void setIndependentYear(Short independentYear) {
        this.independentYear = independentYear;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public double getGdp() {
        return gdp;
    }

    public void setGdp(double gdp) {
        this.gdp = gdp;
    }

    public double getOldGdp() {
        return oldGdp;
    }

    public void setOldGdp(double oldGdp) {
        this.oldGdp = oldGdp;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
