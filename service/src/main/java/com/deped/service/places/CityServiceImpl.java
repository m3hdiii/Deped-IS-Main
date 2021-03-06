package com.deped.service.places;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.location.City;
import com.deped.repository.places.CityRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public ResponseEntity<City> create(City entity) {
        City savedEntity = null;
        try {
            savedEntity = cityRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<City> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(City entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = cityRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, City.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<City>> fetchAll() {
        List<City> citys = cityRepository.fetchAll();
        ResponseEntity<List<City>> responseEntity = new ResponseEntity<>(citys, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<City>> fetchByRange(Range range) {
        List<City> citys = cityRepository.fetchByRange(range);
        ResponseEntity<List<City>> responseEntity = new ResponseEntity<>(citys, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<City> fetchById(Long id) {
        City city = cityRepository.fetchById(id);
        ResponseEntity<City> responseEntity = new ResponseEntity<>(city, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(City... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = cityRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, City.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(City... entities) {
        return null;
    }

    @Override
    public ResponseEntity<List<City>> fetchAllByCountryCode(String countryCode) {
        List<City> cities = cityRepository.fetchAllByCountryCode(countryCode);
        ResponseEntity<List<City>> responseEntity = new ResponseEntity<>(cities, OK);
        return responseEntity;
    }
}
