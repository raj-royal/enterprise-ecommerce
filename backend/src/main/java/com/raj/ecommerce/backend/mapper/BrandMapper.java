
package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.request.CreateBrandRequest;
import com.raj.ecommerce.backend.dto.response.BrandResponse;
import com.raj.ecommerce.backend.entity.Brand;

public class BrandMapper {

    private BrandMapper(){}

    public static Brand toEntity(CreateBrandRequest request){

        return Brand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .build();
    }

    public static BrandResponse toResponse(Brand brand){

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .logoUrl(brand.getLogoUrl())
                .active(brand.getActive())
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .build();
    }

}