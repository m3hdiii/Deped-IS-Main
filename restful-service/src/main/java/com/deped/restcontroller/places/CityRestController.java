package com.deped.restcontroller.places;

import com.deped.model.Response;
import com.deped.model.location.City;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.places.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityRestController extends AbstractMainRestController<City, Long> {

    private static final String BASE_NAME = "city";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_COUNTRY_CODE = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    @Autowired
    private CityService cityService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<City> create(@RequestBody City entity) {
        ResponseEntity<City> response = cityService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody City entity) {
        ResponseEntity<Response> response = cityService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<City>> fetchAll() {
        ResponseEntity<List<City>> response = cityService.fetchAll();
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<City>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<City>> response = cityService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<City> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<City> response = cityService.fetchById(aLong);
        return response;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> remove(@RequestBody City... entities) {
        ResponseEntity<Response> response = cityService.remove(entities);
        return response;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(City... entities) {
        return null;
    }

    @RequestMapping(value = FETCH_MAPPING_BY_COUNTRY_CODE, method = RequestMethod.POST)
    public ResponseEntity<List<City>> fetchAllByCountryCode(@PathVariable(ID_STRING_LITERAL) String countryCode) {
        ResponseEntity<List<City>> response = cityService.fetchAllByCountryCode(countryCode);
        return response;
    }
}
