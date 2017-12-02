package com.deped.restcontroller.order;

import com.deped.model.Response;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsID;
import com.deped.model.order.OrderDetailsState;
import com.deped.repository.utils.Range;
import com.deped.restcontroller.AbstractMainRestController;
import com.deped.service.order.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderDetailsRestController extends AbstractMainRestController<OrderDetails, OrderDetailsID> {
    private static final String BASE_NAME = "order-details";
    private static final String CREATE_MAPPING = BASE_NAME + CREATE_PATTERN;
    private static final String CREATE_ALL_MAPPING = BASE_NAME + CREATE_ALL_PATTERN;
    private static final String ORDER_ALL_MAPPING = BASE_NAME + URL_SEPARATOR + "order-all";
    private static final String UPDATE_MAPPING = BASE_NAME + UPDATE_PATTERN;
    private static final String FETCH_MAPPING = BASE_NAME + FETCH_PATTERN;
    private static final String FETCH_MAPPING_BY_ORDER_ID = BASE_NAME + FETCH_PATTERN + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_RANGE_MAPPING = BASE_NAME + FETCH_PATTERN + RANGE_PATTERN;
    private static final String FETCH_BY_ID_MAPPING = BASE_NAME + FETCH_BY_ID_PATTERN;
    private static final String FETCH_BY_STATES = FETCH_MAPPING + URL_SEPARATOR + "by-states";
    private static final String REMOVE_MAPPING = BASE_NAME + REMOVE_PATTERN;
    private static final String UPDATE_STATE_MAPPING = BASE_NAME + "/update-state/user" + FETCH_BY_ID_PATTERN + "/state/{state}";


    @Autowired
    private OrderDetailsService orderDetailsService;

    @Override
    @RequestMapping(value = CREATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<OrderDetails> create(@RequestBody OrderDetails entity) {
        ResponseEntity<OrderDetails> response = orderDetailsService.create(entity);
        return response;
    }

    @RequestMapping(value = ORDER_ALL_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> requestAll(@RequestBody OrderDetails... entities) {
        ResponseEntity<Response> response = orderDetailsService.orderAll(entities);
        return response;
    }

    @Override
    @RequestMapping(value = UPDATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> update(@RequestBody OrderDetails entity) {
        ResponseEntity<Response> response = orderDetailsService.update(entity);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<OrderDetails>> fetchAll() {
        ResponseEntity<List<OrderDetails>> response = orderDetailsService.fetchAll();
        return response;
    }

    @RequestMapping(value = FETCH_MAPPING_BY_ORDER_ID, method = RequestMethod.POST)
    public ResponseEntity<List<OrderDetails>> fetchAllByOrderId(@PathVariable(ID_STRING_LITERAL) Long id) {
        ResponseEntity<List<OrderDetails>> response = orderDetailsService.fetchAllById(id);
        return response;
    }

    @Override
    @RequestMapping(value = FETCH_BY_RANGE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<OrderDetails>> fetchByRange(@PathVariable(FROM_STRING_LITERAL) int from, @PathVariable(TO_STRING_LITERAL) int to) {
        ResponseEntity<List<OrderDetails>> response = orderDetailsService.fetchByRange(new Range(from, to));
        return response;
    }

    @Override
    public ResponseEntity<OrderDetails> fetchById(OrderDetailsID aLong) {
        return null;
    }

    //TODO Work Later
    @RequestMapping(value = FETCH_BY_ID_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<OrderDetails> fetchByCompositeId(OrderDetailsID aLong) {
        ResponseEntity<OrderDetails> response = orderDetailsService.fetchById(aLong);
        return response;
    }

    @Override
    @RequestMapping(value = REMOVE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> remove(@RequestBody OrderDetails... entities) {
        ResponseEntity<Response> response = orderDetailsService.remove(entities);
        return response;
    }

    @Override
    @RequestMapping(value = CREATE_ALL_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> createOrUpdateAll(@RequestBody OrderDetails... entities) {
        ResponseEntity<Response> response = orderDetailsService.createOrUpdateAll(entities);
        return response;
    }

    @RequestMapping(value = UPDATE_STATE_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<Response> updateOrderStatus(@PathVariable(ID_STRING_LITERAL) String username, @PathVariable("state") Integer orderDetailsState, @RequestBody OrderDetails... entities) {
        OrderDetailsState state = OrderDetailsState.values()[orderDetailsState];
        ResponseEntity<Response> response = orderDetailsService.updateOrderState(username, state, entities);
        return response;
    }

    @RequestMapping(value = FETCH_BY_STATES, method = RequestMethod.POST)
    public ResponseEntity<List<OrderDetails>> fetchByOrderDetailsStates(@RequestBody Integer... orderDetailsStates) {
        List<OrderDetailsState> orderDetailsStateList = new ArrayList<>();
        for (Integer orderDetailsState : orderDetailsStates) {
            OrderDetailsState ods = OrderDetailsState.values()[orderDetailsState];
            orderDetailsStateList.add(ods);
        }

        ResponseEntity<List<OrderDetails>> response = orderDetailsService.fetchAllByStates(orderDetailsStateList);
        return response;
    }
}