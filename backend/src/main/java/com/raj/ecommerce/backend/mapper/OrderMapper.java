

package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.response.OrderItemResponse;
import com.raj.ecommerce.backend.dto.response.OrderResponse;
import com.raj.ecommerce.backend.entity.Order;
import com.raj.ecommerce.backend.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderItemResponse toItemResponse(OrderItem item) {

        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .imageUrl(item.getProduct().getImageUrl())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    public static OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(OrderMapper::toItemResponse)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .customerName(
                        order.getUser().getFirstName()
                                + " "
                                + order.getUser().getLastName()
                )
                .customerEmail(order.getUser().getEmail())
                .phoneNumber(order.getUser().getPhoneNumber())
                .shippingAddress(
                        order.getAddress().getAddressLine1()
                                + ", "
                                + order.getAddress().getCity()
                                + ", "
                                + order.getAddress().getState()
                                + ", "
                                + order.getAddress().getCountry()
                                + " - "
                                + order.getAddress().getPostalCode()
                )
                .totalItems(order.getTotalItems())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}