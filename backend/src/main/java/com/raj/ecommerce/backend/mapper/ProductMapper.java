
package com.raj.ecommerce.backend.mapper;

import com.raj.ecommerce.backend.dto.request.CreateProductRequest;
import com.raj.ecommerce.backend.dto.response.ProductResponse;
import com.raj.ecommerce.backend.entity.Product;

public class ProductMapper {

    private ProductMapper(){}

    public static Product toEntity(CreateProductRequest request){

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .discountPrice(request.getDiscountPrice())
                .quantity(request.getQuantity())
                .sku(request.getSku())
                .imageUrl(request.getImageUrl())
                .featured(request.getFeatured())
                .build();
    }

    public static ProductResponse toResponse(Product product){

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .quantity(product.getQuantity())
                .sku(product.getSku())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .featured(product.getFeatured())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}