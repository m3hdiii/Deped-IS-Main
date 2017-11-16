package com.deped.service.order;

import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    ResponseEntity<List<Order>> fetchAllByOrderState(OrderState orderState);

    ResponseEntity<List<Order>> fetchAllByUserId(Long userId);

    ResponseEntity<List<Order>> fetchAllByStates(List<OrderState> orderStates);
}
