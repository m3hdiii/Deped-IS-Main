package com.deped.repository.order;

import com.deped.model.order.Order;
import com.deped.model.order.OrderState;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> fetchAllByOrderState(OrderState orderState);

    List<Order> fetchAllByUserId(Long userId);
}
