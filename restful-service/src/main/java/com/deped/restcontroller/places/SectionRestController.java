package com.deped.restcontroller.places;

import com.deped.model.Response;
import com.deped.model.location.office.Section;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.places.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SectionRestController extends AbstractMainRestController<Section, Long> {

    private static final String BASE_NAME = "section";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_DEPARTMENT_ID = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    @Autowired
    private SectionService sectionService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Section> create(@RequestBody Section entity) {
        ResponseEntity<Section> response = sectionService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody Section entity) {
        ResponseEntity<Response> response = sectionService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Section>> fetchAll() {
        ResponseEntity<List<Section>> response = sectionService.fetchAll();
        return response;
    }

    @RequestMapping(value = FETCH_MAPPING_BY_DEPARTMENT_ID, method = RequestMethod.POST)
    public ResponseEntity<List<Section>> fetchAllByDepartmentId(@PathVariable(ID_STRING_LITERAL) Long departmentId) {
        ResponseEntity<List<Section>> response = sectionService.fetchAllByDepartment(departmentId);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Section>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<Section>> response = sectionService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<Section> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Section> response = sectionService.fetchById(aLong);
        return response;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> remove(@RequestBody Section... entities) {
        ResponseEntity<Response> response = sectionService.remove(entities);
        return response;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Section... entities) {
        return null;
    }

//    @RequestMapping(value = FETCH_MAPPING_BY_COUNTRY_CODE, method = RequestMethod.POST)
//    public ResponseEntity<List<City>> fetchAllByCountryCode(@PathVariable(ID_STRING_LITERAL)String countryCode) {
//        ResponseEntity<List<City>> response = cityService.fetchAllByCountryCode(countryCode);
//        return response;
//    }
}
