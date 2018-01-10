package com.deped.service.order;

import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.model.search.OrderSearch;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService extends BaseService<Order, Long> {
    ResponseEntity<List<Order>> fetchAllByOrderState(OrderState orderState);

    ResponseEntity<List<Order>> fetchAllByUsername(String username);

    ResponseEntity<List<Order>> fetchAllByStates(List<OrderState> orderStates);

    ResponseEntity<List<Order>> orderSearch(OrderSearch orderSearch);
}
