
package com.raj.ecommerce.backend.controller;

import com.raj.ecommerce.backend.dto.request.CreateBrandRequest;
import com.raj.ecommerce.backend.dto.request.UpdateBrandRequest;
import com.raj.ecommerce.backend.dto.response.BrandResponse;
import com.raj.ecommerce.backend.service.interfaces.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponse> createBrand(
            @Valid @RequestBody CreateBrandRequest request) {

        return new ResponseEntity<>(
                brandService.createBrand(request),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {

        return ResponseEntity.ok(
                brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                brandService.getBrandById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBrandRequest request) {

        return ResponseEntity.ok(
                brandService.updateBrand(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBrand(
            @PathVariable Long id) {

        brandService.deleteBrand(id);

        return ResponseEntity.ok("Brand deleted successfully");
    }
}