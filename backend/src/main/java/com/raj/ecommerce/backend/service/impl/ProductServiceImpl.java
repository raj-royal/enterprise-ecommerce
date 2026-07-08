
package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.CreateProductRequest;
import com.raj.ecommerce.backend.dto.request.UpdateProductRequest;
import com.raj.ecommerce.backend.dto.response.ProductResponse;
import com.raj.ecommerce.backend.entity.Brand;
import com.raj.ecommerce.backend.entity.Category;
import com.raj.ecommerce.backend.entity.Product;
import com.raj.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.ProductMapper;
import com.raj.ecommerce.backend.repository.BrandRepository;
import com.raj.ecommerce.backend.repository.CategoryRepository;
import com.raj.ecommerce.backend.repository.ProductRepository;
import com.raj.ecommerce.backend.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        if(productRepository.existsBySku(request.getSku())){
            throw new ResourceAlreadyExistsException("SKU already exists");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        Product product = ProductMapper.toEntity(request);

        product.setCategory(category);
        product.setBrand(brand);

        Product saved = productRepository.save(product);

        return ProductMapper.toResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id,
                                         UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        productRepository.findBySku(request.getSku())
                .ifPresent(existing -> {
                    if(!existing.getId().equals(id)){
                        throw new ResourceAlreadyExistsException("SKU already exists");
                    }
                });

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setQuantity(request.getQuantity());
        product.setSku(request.getSku());
        product.setImageUrl(request.getImageUrl());
        product.setActive(request.getActive());
        product.setFeatured(request.getFeatured());

        product.setCategory(category);
        product.setBrand(brand);

        Product updated = productRepository.save(product);

        return ProductMapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }


//     seacrh products

    @Override
    public Page<ProductResponse> searchProducts(
            String keyword,
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        return productRepository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(ProductMapper::toResponse);
    }

//     products by category

    @Override
    public Page<ProductResponse> getProductsByCategory(
            Long categoryId,
            int page,
            int size) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        Pageable pageable =
                PageRequest.of(page, size);

        return productRepository
                .findByCategory(category, pageable)
                .map(ProductMapper::toResponse);
    }

//     products by brand
@Override
public Page<ProductResponse> getProductsByBrand(
        Long brandId,
        int page,
        int size) {

    Brand brand = brandRepository.findById(brandId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Brand not found"));

    Pageable pageable =
            PageRequest.of(page, size);

    return productRepository
            .findByBrand(brand, pageable)
            .map(ProductMapper::toResponse);
}

//  featured products

    @Override
    public Page<ProductResponse> getFeaturedProducts(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return productRepository
                .findByFeaturedTrue(pageable)
                .map(ProductMapper::toResponse);
    }

}