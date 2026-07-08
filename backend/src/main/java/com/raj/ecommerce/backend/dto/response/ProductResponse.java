
package com.raj.ecommerce.backend.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer quantity;

    private String sku;

    private String imageUrl;

    private Boolean active;

    private Boolean featured;

    private Long categoryId;

    private String categoryName;

    private Long brandId;

    private String brandName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}