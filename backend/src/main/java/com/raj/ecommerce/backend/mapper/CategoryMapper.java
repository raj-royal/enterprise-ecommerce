

package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.request.CreateCategoryRequest;
import com.raj.ecommerce.backend.dto.response.CategoryResponse;
import com.raj.ecommerce.backend.entity.Category;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toEntity(CreateCategoryRequest request) {

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .active(category.getActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}