

package com.raj.ecommerce.backend.dto.response;

import com.raj.ecommerce.backend.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private String customerName;

    private String customerEmail;

    private String phoneNumber;

    private String shippingAddress;

    private Integer totalItems;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

}