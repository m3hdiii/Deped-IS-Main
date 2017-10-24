package com.deped.restcontroller.brand;

import com.deped.model.Response;
import com.deped.model.brand.Brand;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandRestController extends AbstractMainRestController<Brand, Long> {

    private static final String BASE_NAME = "brand";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;


    @Autowired
    private BrandService brandService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Brand> create(@RequestBody Brand entity) {
        ResponseEntity<Brand> response = brandService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody Brand entity) {
        ResponseEntity<Response> response = brandService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Brand>> fetchAll() {
        ResponseEntity<List<Brand>> response = brandService.fetchAll();
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Brand>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<Brand>> response = brandService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<Brand> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Brand> response = brandService.fetchById(aLong);
        return response;
    }

    @Override
    public @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    ResponseEntity<Response> remove(@RequestBody Brand... entities) {
        ResponseEntity<Response> response = brandService.remove(entities);
        return response;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Brand... entities) {
        return null;
    }
}
