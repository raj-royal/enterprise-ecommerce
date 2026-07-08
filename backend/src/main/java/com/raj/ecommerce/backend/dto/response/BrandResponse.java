
package com.raj.ecommerce.backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponse {

    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}