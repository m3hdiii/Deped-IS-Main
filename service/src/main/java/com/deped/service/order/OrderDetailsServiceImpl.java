package com.deped.service.order;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsID;
import com.deped.model.order.OrderDetailsState;
import com.deped.repository.order.OrderDetailsRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public ResponseEntity<OrderDetails> create(OrderDetails entity) {
        OrderDetails savedEntity = null;
        try {
            savedEntity = orderDetailsRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<OrderDetails> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(OrderDetails entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = orderDetailsRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<OrderDetails>> fetchAll() {
        List<OrderDetails> brands = orderDetailsRepository.fetchAll();
        ResponseEntity<List<OrderDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<OrderDetails>> fetchByRange(Range range) {
        List<OrderDetails> brands = orderDetailsRepository.fetchByRange(range);
        ResponseEntity<List<OrderDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<OrderDetails> fetchById(OrderDetailsID id) {
        OrderDetails brand = orderDetailsRepository.fetchById(id);
        ResponseEntity<OrderDetails> responseEntity = new ResponseEntity<>(brand, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(OrderDetails... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = orderDetailsRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(OrderDetails... entities) {
        Boolean isCreated = null;
        try {
            isCreated = orderDetailsRepository.createOrUpdateAll(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
        }
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> updateOrderState(String username, OrderDetailsState orderDetailsState, OrderDetails... entities) {
        Boolean isUpdated = orderDetailsRepository.updateOrderState(username, orderDetailsState, entities);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> orderAll(OrderDetails... entities) {
        Boolean isCreated = orderDetailsRepository.orderAll(entities);
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<OrderDetails>> fetchAllById(Long requestId) {
        List<OrderDetails> brands = orderDetailsRepository.fetchAllById(requestId);
        ResponseEntity<List<OrderDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<OrderDetails>> fetchAllByStates(List<OrderDetailsState> orderDetailsStates) {
        List<OrderDetails> brands = orderDetailsRepository.fetchAllByStates(orderDetailsStates);
        ResponseEntity<List<OrderDetails>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }
}
