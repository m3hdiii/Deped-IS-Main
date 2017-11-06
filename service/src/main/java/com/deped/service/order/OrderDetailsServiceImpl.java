package com.deped.service.order;

import com.deped.model.Operation;
import com.deped.model.Response;
import com.deped.model.order.OrderDetails;
import com.deped.repository.order.OrderDetailsRepository;
import com.deped.repository.utils.Range;
import com.deped.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public ResponseEntity<OrderDetails> create(OrderDetails entity) {
        OrderDetails savedEntity = orderDetailsRepository.create(entity);
        ResponseEntity<OrderDetails> responseEntity = new ResponseEntity<>(savedEntity, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> update(OrderDetails entity) {
        Boolean isUpdated = orderDetailsRepository.update(entity);
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
    public ResponseEntity<OrderDetails> fetchById(Object id) {
        OrderDetails brand = orderDetailsRepository.fetchById(id);
        ResponseEntity<OrderDetails> responseEntity = new ResponseEntity<>(brand, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> remove(OrderDetails... entities) {
        Boolean isRemoved = orderDetailsRepository.remove(entities);
        Response response = ServiceUtils.makeResponse(isRemoved, Operation.DELETE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> createOrUpdateAll(OrderDetails... entities) {
        Boolean isCreated = orderDetailsRepository.createOrUpdateAll(entities);
        Response response = ServiceUtils.makeResponse(isCreated, Operation.CREATE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> updateOrderStatus(OrderDetails[] entities) {
        Boolean isUpdated = orderDetailsRepository.updateOrderStatus(entities);
        Response response = ServiceUtils.makeResponse(isUpdated, Operation.UPDATE, OrderDetails.class);
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, OK);
        return responseEntity;
    }
}
