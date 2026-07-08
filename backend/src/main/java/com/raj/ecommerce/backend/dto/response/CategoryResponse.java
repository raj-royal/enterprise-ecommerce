

package com.raj.ecommerce.backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}