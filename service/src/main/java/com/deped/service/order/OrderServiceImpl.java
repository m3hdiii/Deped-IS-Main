package com.deped.service.order;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.repository.order.OrderRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<Order> create(Order entity) {
        Order savedEntity = null;
        try {
            savedEntity = orderRepository.create(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        ResponseEntity<Order> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Order entity) {
        Boolean isUpdated = null;
        try {
            isUpdated = orderRepository.update(entity);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Order.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Order>> fetchAll() {
        List<Order> categories = orderRepository.fetchAll();
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(categories, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Order>> fetchByRange(Range range) {
        List<Order> categories = orderRepository.fetchByRange(range);
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(categories, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Order> fetchById(Long id) {
        Order category = orderRepository.fetchById(id);
        ResponseEntity<Order> responseEntity = new ResponseEntity<>(category, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Order... entities) {
        Boolean isRemoved = null;
        try {
            isRemoved = orderRepository.remove(entities);
        } catch (DatabaseRolesViolationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Order.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(Order... entities) {
        return null;
    }

    @Override
    public ResponseEntity<List<Order>> fetchAllByOrderState(OrderState orderState) {
        List<Order> requests = orderRepository.fetchAllByOrderState(orderState);
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Order>> fetchAllByUsername(String username) {
        List<Order> requests = orderRepository.fetchAllByUsername(username);
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(requests, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Order>> fetchAllByStates(List<OrderState> orderStates) {
        List<Order> brands = orderRepository.fetchAllByStates(orderStates);
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(brands, OK);
        return responseEntity;
    }

}
