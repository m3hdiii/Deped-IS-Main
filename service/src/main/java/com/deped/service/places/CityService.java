package com.deped.service.places;

import com.deped.model.location.City;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CityService extends BaseService<City> {
    ResponseEntity<List<City>> fetchAllByCountryCode(String countryCode);
}
