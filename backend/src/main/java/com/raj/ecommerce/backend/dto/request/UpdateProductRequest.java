

package com.raj.ecommerce.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private BigDecimal discountPrice;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String sku;

    private String imageUrl;

    private Boolean active;

    private Boolean featured;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;
}