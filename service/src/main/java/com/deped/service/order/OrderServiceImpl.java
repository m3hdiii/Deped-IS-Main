package com.deped.service.order;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.order.Order;
import com.deped.repository.order.OrderRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository categoryRepository;

    @Override
    public ResponseEntity<Order> create(Order entity) {
        Order savedEntity = categoryRepository.create(entity);
        ResponseEntity<Order> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(Order entity) {
        Boolean isUpdated = categoryRepository.update(entity);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, Order.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Order>> fetchAll() {
        List<Order> categories = categoryRepository.fetchAll();
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(categories, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Order>> fetchByRange(Range range) {
        List<Order> categories = categoryRepository.fetchByRange(range);
        ResponseEntity<List<Order>> responseEntity = new ResponseEntity<>(categories, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Order> fetchById(Object id) {
        Order category = categoryRepository.fetchById(id);
        ResponseEntity<Order> responseEntity = new ResponseEntity<>(category, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(Order... entities) {
        Boolean isRemoved = categoryRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, Order.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
