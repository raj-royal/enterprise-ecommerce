
package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.response.CartItemResponse;
import com.raj.ecommerce.backend.dto.response.CartResponse;
import com.raj.ecommerce.backend.entity.Cart;
import com.raj.ecommerce.backend.entity.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    private CartMapper() {
    }

    public static CartItemResponse toItemResponse(CartItem item) {

        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .imageUrl(item.getProduct().getImageUrl())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .totalPrice(
                        item.getPrice()
                                .multiply(java.math.BigDecimal.valueOf(item.getQuantity()))
                )
                .build();
    }

    public static CartResponse toResponse(Cart cart) {

        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(CartMapper::toItemResponse)
                .collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .totalItems(cart.getTotalItems())
                .totalPrice(cart.getTotalPrice())
                .items(items)
                .build();
    }
}