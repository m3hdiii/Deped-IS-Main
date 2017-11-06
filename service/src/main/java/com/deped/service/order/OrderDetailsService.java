package com.deped.service.order;

import com.deped.model.Response;
import com.deped.model.order.OrderDetails;
import com.deped.service.BaseService;
import org.springframework.http.ResponseEntity;

public interface OrderDetailsService extends BaseService<OrderDetails> {

    ResponseEntity<Response> updateOrderStatus(OrderDetails[] entities);
}
