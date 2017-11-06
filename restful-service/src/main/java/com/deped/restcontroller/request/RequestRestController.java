package com.deped.restcontroller.request;

import com.deped.model.Response;
import com.deped.model.request.Request;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestRestController extends AbstractMainRestController<Request, Long> {

    private static final String BASE_NAME = "request";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;


    @Autowired
    private RequestService requestService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Request> create(@RequestBody Request entity) {
        ResponseEntity<Request> response = requestService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody Request entity) {
        ResponseEntity<Response> response = requestService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Request>> fetchAll() {
        ResponseEntity<List<Request>> response = requestService.fetchAll();
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Request>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<Request>> response = requestService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<Request> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Request> response = requestService.fetchById(aLong);
        return response;
    }

    @Override
    public @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    ResponseEntity<Response> remove(@RequestBody Request... entities) {
        ResponseEntity<Response> response = requestService.remove(entities);
        return response;
    }

    @Override
    public @RequestMapping(value = CREATE_ALL_PATTERN, method = RequestMethod.POST)
    ResponseEntity<Response> createOrUpdateAll(@RequestBody Request... entities) {
        return null;
    }
}
