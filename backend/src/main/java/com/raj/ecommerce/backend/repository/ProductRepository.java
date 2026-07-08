
package com.raj.ecommerce.backend.repository;

import com.raj.ecommerce.backend.entity.Brand;
import com.raj.ecommerce.backend.entity.Category;
import com.raj.ecommerce.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    // Search by product name
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // Filter by category
    Page<Product> findByCategory(Category category, Pageable pageable);

    // Filter by brand
    Page<Product> findByBrand(Brand brand, Pageable pageable);

    // Featured products
    Page<Product> findByFeaturedTrue(Pageable pageable);

    // Active products
    Page<Product> findByActiveTrue(Pageable pageable);
}