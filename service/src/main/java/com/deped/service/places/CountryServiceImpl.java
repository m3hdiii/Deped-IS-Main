package com.deped.service.places;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.location.Country;
import com.deped.repository.places.CountryRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public ResponseEntity<Country> create(Country entity) {
        Country savedEntity = countryRepository.create(entity);
        ResponseEntity<Country> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Country entity) {
        Boolean isUpdated = countryRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Country.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Country>> fetchAll() {
        List<Country> countrys = countryRepository.fetchAll();
        ResponseEntity<List<Country>> responseEntity = new ResponseEntity<>(countrys, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Country>> fetchByRange(Range range) {
        List<Country> countrys = countryRepository.fetchByRange(range);
        ResponseEntity<List<Country>> responseEntity = new ResponseEntity<>(countrys, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Country> fetchById(Object id) {
        Country country = countryRepository.fetchById(id);
        ResponseEntity<Country> responseEntity = new ResponseEntity<>(country, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Country... entities) {
        Boolean isRemoved = countryRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Country.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Country... entities) {
        return null;
    }
}
