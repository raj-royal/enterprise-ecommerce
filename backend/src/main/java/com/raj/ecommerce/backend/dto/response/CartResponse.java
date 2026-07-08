package com.raj.ecommerce.backend.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long id;

    private Integer totalItems;

    private BigDecimal totalPrice;

    private List<CartItemResponse> items;

}