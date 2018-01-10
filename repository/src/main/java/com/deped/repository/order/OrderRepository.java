package com.deped.repository.order;

import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.model.search.OrderSearch;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long> {
    List<Order> fetchAllByOrderState(OrderState orderState);

    List<Order> fetchAllByUsername(String username);

    List<Order> fetchAllByStates(List<OrderState> orderStates);

    List<Order> orderSearch(OrderSearch orderSearch);
}
