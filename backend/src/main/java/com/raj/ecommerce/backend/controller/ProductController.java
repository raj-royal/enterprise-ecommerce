
package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.CreateProductRequest;
import com.raj.ecommerce.backend.dto.request.UpdateProductRequest;
import com.raj.ecommerce.backend.dto.response.ProductResponse;
import com.raj.ecommerce.backend.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request){

        return new ResponseEntity<>(
                productService.createProduct(request),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request){

        return ResponseEntity.ok(
                productService.updateProduct(id,request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> searchProducts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "name")
            String sortBy) {

        return ResponseEntity.ok(
                productService.searchProducts(
                        keyword,
                        page,
                        size,
                        sortBy));
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByCategory(

            @PathVariable Long categoryId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(
                        categoryId,
                        page,
                        size));
    }



    @GetMapping("/brand/{brandId}")
    public ResponseEntity<Page<ProductResponse>> getByBrand(

            @PathVariable Long brandId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                productService.getProductsByBrand(
                        brandId,
                        page,
                        size));
    }




    @GetMapping("/featured")
    public ResponseEntity<Page<ProductResponse>> featuredProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                productService.getFeaturedProducts(
                        page,
                        size));
    }
}