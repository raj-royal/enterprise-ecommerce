

package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.PlaceOrderRequest;
import com.raj.ecommerce.backend.dto.response.OrderResponse;
import com.raj.ecommerce.backend.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody PlaceOrderRequest request) {

        return ResponseEntity.ok(
                orderService.placeOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders() {

        return ResponseEntity.ok(
                orderService.getMyOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.getOrderById(id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.cancelOrder(id));
    }

}