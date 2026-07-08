
package com.raj.ecommerce.backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String imageUrl;

    private String categoryName;

    private String brandName;

    private String price;

    private String discountPrice;

    private LocalDateTime createdAt;
}