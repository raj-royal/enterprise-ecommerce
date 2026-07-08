

package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.PlaceOrderRequest;
import com.raj.ecommerce.backend.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(PlaceOrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse cancelOrder(Long id);

}