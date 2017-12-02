package com.deped.repository.places;

import com.deped.model.location.City;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface CityRepository extends BaseRepository<City, Long> {
    List<City> fetchAllByCountryCode(String countryCode);
}
