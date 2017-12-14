package com.deped.repository.order;

import com.deped.model.items.ItemType;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsID;
import com.deped.model.order.OrderDetailsState;
import com.deped.repository.BaseRepository;

import java.util.List;

public interface OrderDetailsRepository extends BaseRepository<OrderDetails, OrderDetailsID> {
    boolean updateOrderState(String username, OrderDetailsState orderDetailsState, OrderDetails... entities);

    boolean orderAll(OrderDetails... entities);

    List<OrderDetails> fetchAllById(Long requestId);

    List<OrderDetails> fetchAllByIdAndItemType(Long orderId, ItemType[] itemTypes);

    List<OrderDetails> fetchAllByStates(List<OrderDetailsState> orderDetailsStates);
}
