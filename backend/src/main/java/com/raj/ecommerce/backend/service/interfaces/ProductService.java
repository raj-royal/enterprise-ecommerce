

package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.CreateProductRequest;
import com.raj.ecommerce.backend.dto.request.UpdateProductRequest;
import com.raj.ecommerce.backend.dto.response.ProductResponse;

import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id);

    Page<ProductResponse> searchProducts(
            String keyword,
            int page,
            int size,
            String sortBy);

    Page<ProductResponse> getProductsByCategory(
            Long categoryId,
            int page,
            int size);


    Page<ProductResponse> getProductsByBrand(
            Long brandId,
            int page,
            int size);


    Page<ProductResponse> getFeaturedProducts(
            int page,
            int size);

}