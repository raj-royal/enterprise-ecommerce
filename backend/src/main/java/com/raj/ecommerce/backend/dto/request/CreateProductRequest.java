
package com.raj.ecommerce.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    private BigDecimal discountPrice;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotBlank
    private String sku;

    private String imageUrl;

    private Boolean featured;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;
}