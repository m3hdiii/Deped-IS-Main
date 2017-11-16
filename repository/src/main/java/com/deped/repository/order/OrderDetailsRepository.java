package com.deped.repository.order;

import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsState;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface OrderDetailsRepository extends BaseRepository<OrderDetails> {
    boolean updateOrderState(Long userId, OrderDetailsState orderDetailsState, OrderDetails... entities);

    boolean orderAll(OrderDetails... entities);

    List<OrderDetails> fetchAllById(Long requestId);

    List<OrderDetails> fetchAllByStates(List<OrderDetailsState> orderDetailsStates);
}
