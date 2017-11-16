package com.deped.model.location;

import com.deped.protection.validators.xss.XSS;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import static com.deped.repository.utils.ConstantValues.FETCH_ALL_COUNTRIES;

@NamedQueries({
        @NamedQuery(
                name = FETCH_ALL_COUNTRIES,
                query = "SELECT c FROM Country c"
        )
})
@Entity
@Table(name = "country")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "countryCode", scope = Country.class)
public class Country {

    @Column(name = "country_code")
    @Id
    private String countryCode;

    @Column(name = "name")
    @Length(min = 2, max = 45, message = "name must be between 2 to 45 character character")
    @NotEmpty(message = "Name field can not be blank")
    @XSS
    private String name;

    @Column(name = "continent")
    @Enumerated(EnumType.STRING)
    private Continent continent;

    @Column(name = "region")
    private String region;

    @Column(name = "surface_area")
    private Double surfaceArea;

    @Column(name = "indep_year")
    private Short independentYear;

    @Column(name = "population")
    private Long population;

    @Column(name = "life_expectancy")
    private Double lifeExpectancy;

    @Column(name = "gnp")
    private Double gdp;

    @Column(name = "gnp_old")
    private Double oldGdp;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "government_form")
    private String governmentForm;

    @Column(name = "head_of_state")
    private String headOfState;

    @JoinColumn(name = "capital")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonBackReference("country-binding")
    private City capital;

    @Column(name = "code2")
    private String code2;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY)
//    @JsonBackReference("country-binding")
//    private List<City> cities;


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

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
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

    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public Double getGdp() {
        return gdp;
    }

    public void setGdp(Double gdp) {
        this.gdp = gdp;
    }

    public Double getOldGdp() {
        return oldGdp;
    }

    public void setOldGdp(Double oldGdp) {
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

//    public List<City> getCities() {
//        return cities;
//    }
//
//    public void setCities(List<City> cities) {
//        this.cities = cities;
//    }
}
