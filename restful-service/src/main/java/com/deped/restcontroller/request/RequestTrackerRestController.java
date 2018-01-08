package com.deped.restcontroller.request;

import com.deped.model.Response;
import com.deped.model.request.RequestDetailsStatus;
import com.deped.model.tracker.RequestTracker;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.request.RequestTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestTrackerRestController extends AbstractMainRestController<RequestTracker, String> {
    private static final String BASE_NAME = "request-tracker";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String CREATE_ALL_MAPPING = BASE_NAME + CREATE_ALL_PATTERN;
    private static final String ORDER_ALL_MAPPING = BASE_NAME + URL_SEPARATOR + "order-all";
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_ORDER_ID = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_MAPPING_BY_ORDER_ID_AND_ITEM_TYPE = BASE_NAME + "/find-by-order-type" + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_ITEM_NAME_MAPPING = BASE_NAME + "/fetch-by-item-name" + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_STATES = FETCH_MAPPING + URL_SEPARATOR + "by-states";
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String UPDATE_STATE_MAPPING = BASE_NAME + "/update-state/user" + FETCH_BY_ID_PATTERN + "/state/{state}";
    private static final String REQUEST_ALL_MAPPING = BASE_NAME + URL_SEPARATOR + "request-all";

    private static final String UPDATE_STATUS_MAPPING = BASE_NAME + "/update-status/user" + FETCH_BY_ID_PATTERN + "/status/{state}";

    @Autowired
    private RequestTrackerService requestTrackerService;

    @Override
    public ResponseEntity<RequestTracker> create(RequestTracker entity) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(RequestTracker entity) {
        return null;
    }

    @Override
    public ResponseEntity<List<RequestTracker>> fetchAll() {
        return null;
    }

    @Override
    public ResponseEntity<List<RequestTracker>> fetchByRange(int from, int to) {
        return null;
    }

    @Override
    public ResponseEntity<RequestTracker> fetchById(String s) {
        return null;
    }

    @Override
    public ResponseEntity<Response> remove(RequestTracker... entities) {
        return null;
    }

    @Override
    @RequestMapping(value = REQUEST_ALL_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> createOrUpdateAll(RequestTracker... entities) {
        ResponseEntity<Response> response = requestTrackerService.createOrUpdateAll(entities);
        return response;
    }

    @RequestMapping(value = UPDATE_STATUS_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> updateOrderStatus(@PathVariable(ID_STRING_LITERAL) String username, @PathVariable("state") Integer orderDetailsState, @RequestBody RequestTracker... entities) {
        RequestDetailsStatus status = RequestDetailsStatus.values()[orderDetailsState];
        ResponseEntity<Response> response = requestTrackerService.updateRequestStatus(username, status, entities);
        return response;
    }
}
