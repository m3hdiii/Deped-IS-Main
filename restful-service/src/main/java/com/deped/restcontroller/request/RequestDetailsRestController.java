package com.deped.restcontroller.request;

import com.deped.model.Response;
import com.deped.model.request.RequestDetails;
import com.deped.model.request.RequestDetailsID;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.request.RequestDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestDetailsRestController extends AbstractMainRestController<RequestDetails, RequestDetailsID> {
    private static final String BASE_NAME = "request-details";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String CREATE_ALL_MAPPING = BASE_NAME + CREATE_ALL_PATTERN;
    private static final String REQUEST_ALL_MAPPING = BASE_NAME + URL_SEPARATOR + "request-all";
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_REQUEST_ID = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;

    private static final String UPDATE_STATUS_MAPPING = BASE_NAME + "/update-status/user" + FETCH_BY_ID_PATTERN + "/status/{state}";


    @Autowired
    private RequestDetailsService requestDetailsService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<RequestDetails> create(@RequestBody RequestDetails entity) {
        ResponseEntity<RequestDetails> response = requestDetailsService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody RequestDetails entity) {
        ResponseEntity<Response> response = requestDetailsService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<RequestDetails>> fetchAll() {
        ResponseEntity<List<RequestDetails>> response = requestDetailsService.fetchAll();
        return response;
    }


    @RequestMapping(value = FETCH_MAPPING_BY_REQUEST_ID, method = RequestMethod.POST)
    public ResponseEntity<List<RequestDetails>> fetchAllByRequestId(@PathVariable(ID_STRING_LITERAL) Long id) {
        ResponseEntity<List<RequestDetails>> response = requestDetailsService.fetchAllById(id);
        return response;
    }


    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<RequestDetails>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<RequestDetails>> response = requestDetailsService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    public ResponseEntity<RequestDetails> fetchById(RequestDetailsID aLong) {
        return null;
    }

    //TODO Work Later
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<RequestDetails> fetchByCompositeId(RequestDetailsID aLong) {
        ResponseEntity<RequestDetails> response = requestDetailsService.fetchById(aLong);
        return response;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> remove(@RequestBody RequestDetails... entities) {
        ResponseEntity<Response> response = requestDetailsService.remove(entities);
        return response;
    }

    @Override
    @RequestMapping(value = CREATE_ALL_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> createOrUpdateAll(@RequestBody RequestDetails... entities) {
        ResponseEntity<Response> response = requestDetailsService.createOrUpdateAll(entities);
        return response;
    }

    @RequestMapping(value = REQUEST_ALL_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> requestAll(@RequestBody RequestDetails... entities) {
        ResponseEntity<Response> response = requestDetailsService.requestAll(entities);
        return response;
    }

    @RequestMapping(value = UPDATE_STATUS_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> updateOrderStatus(@PathVariable(ID_STRING_LITERAL) Long userId, @PathVariable("state") Integer orderDetailsState, @RequestBody RequestDetails... entities) {
        RequestDetailsStatus status = RequestDetailsStatus.values()[orderDetailsState];
        ResponseEntity<Response> response = requestDetailsService.updateRequestStatus(userId, status, entities);
        return response;
    }
}