package com.deped.restcontroller.order;

import com.deped.model.Response;
import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.model.search.OrderSearch;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController extends AbstractMainRestController<Order, Long> {

    private static final String BASE_NAME = "order";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_OPERATION = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_MAPPING_BY_USER = BASE_NAME + FETCH_PATTERN + "/user" + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_STATES = FETCH_MAPPING + URL_SEPARATOR + "by-states";
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String SEARCH_REQUEST_MAPPING = BASE_NAME + URL_SEPARATOR + "search-list";


    @Autowired
    private OrderService orderService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Order> create(@RequestBody Order entity) {
        ResponseEntity<Order> response = orderService.create(entity);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody Order entity) {
        ResponseEntity<Response> response = orderService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Order>> fetchAll() {
        ResponseEntity<List<Order>> response = orderService.fetchAll();
        return response;
    }

    @RequestMapping(value = FETCH_MAPPING_BY_OPERATION, method = RequestMethod.POST)
    public ResponseEntity<List<Order>> fetchAllByOperation(@PathVariable(ID_STRING_LITERAL) int statusOrdinal) {
        OrderState status = OrderState.values()[statusOrdinal];

        switch (status) {
            case FINALIZED:
            case SAVED:
                return null;
            case PENDING:
            case ORDERED:
            case CONSIDERED:
            case PARTIALLY_ARRIVED:
                break;
        }

        ResponseEntity<List<Order>> response = orderService.fetchAllByOrderState(status);
        return response;
    }

    @RequestMapping(value = FETCH_MAPPING_BY_USER, method = RequestMethod.POST)
    public ResponseEntity<List<Order>> fetchAllByUsername(@PathVariable(ID_STRING_LITERAL) String username) {
        ResponseEntity<List<Order>> response = orderService.fetchAllByUsername(username);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Order>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<Order>> response = orderService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<Order> fetchById(@PathVariable(ID_STRING_LITERAL) Long aLong) {
        ResponseEntity<Order> response = orderService.fetchById(aLong);
        return response;
    }

    @Override
    public @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    ResponseEntity<Response> remove(@RequestBody Order... entities) {
        ResponseEntity<Response> response = orderService.remove(entities);
        return response;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Order... entities) {
        return null;
    }

    @RequestMapping(value = FETCH_BY_STATES, method = RequestMethod.POST)
    public ResponseEntity<List<Order>> fetchByOrderDetailsStates(@RequestBody Integer... orderDetailsStates) {
        List<OrderState> orderDetailsStateList = new ArrayList<>();
        for (Integer orderDetailsState : orderDetailsStates) {
            OrderState ods = OrderState.values()[orderDetailsState];
            orderDetailsStateList.add(ods);
        }

        ResponseEntity<List<Order>> response = orderService.fetchAllByStates(orderDetailsStateList);
        return response;
    }

    @RequestMapping(value = SEARCH_REQUEST_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<Order>> fetchRequestSearch(@RequestBody OrderSearch orderSearch) {
        ResponseEntity<List<Order>> requestList = orderService.orderSearch(orderSearch);
        return requestList;
    }
}