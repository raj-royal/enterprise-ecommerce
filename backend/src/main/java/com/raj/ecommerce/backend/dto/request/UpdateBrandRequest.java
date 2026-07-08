

package com.raj.ecommerce.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBrandRequest {

    @NotBlank(message = "Brand name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    private String logoUrl;

    private Boolean active;
}