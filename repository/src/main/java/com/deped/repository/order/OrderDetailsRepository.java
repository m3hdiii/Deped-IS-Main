package com.deped.repository.order;

import com.deped.model.order.OrderDetails;
import com.deped.repository.BaseRepository;

public interface OrderDetailsRepository extends BaseRepository<OrderDetails> {
    boolean updateOrderStatus(OrderDetails[] entities);
}
