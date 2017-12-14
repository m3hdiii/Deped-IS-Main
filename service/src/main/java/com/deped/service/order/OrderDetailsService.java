package com.deped.service.order;

import com.deped.model.Response;
import com.deped.model.items.ItemType;
import com.deped.model.order.OrderDetails;
import com.deped.model.order.OrderDetailsID;
import com.deped.model.order.OrderDetailsState;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderDetailsService extends BaseService<OrderDetails, OrderDetailsID> {

    ResponseEntity<Response> updateOrderState(String username, OrderDetailsState orderDetailsState, OrderDetails... entities);

    ResponseEntity<Response> orderAll(OrderDetails... entities);

    ResponseEntity<List<OrderDetails>> fetchAllById(Long orderId);

    ResponseEntity<List<OrderDetails>> fetchAllByStates(List<OrderDetailsState> orderDetailsStates);

    ResponseEntity<List<OrderDetails>> fetchAllByIdAndItemType(Long orderId, ItemType[] itemTypes);
}
