
package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.CreateBrandRequest;
import com.raj.ecommerce.backend.dto.request.UpdateBrandRequest;
import com.raj.ecommerce.backend.dto.response.BrandResponse;
import com.raj.ecommerce.backend.entity.Brand;
import com.raj.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.BrandMapper;
import com.raj.ecommerce.backend.repository.BrandRepository;
import com.raj.ecommerce.backend.service.interfaces.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandResponse createBrand(CreateBrandRequest request) {

        if (brandRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Brand already exists");
        }

        Brand brand = BrandMapper.toEntity(request);

        Brand savedBrand = brandRepository.save(brand);

        return BrandMapper.toResponse(savedBrand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {

        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponse getBrandById(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        return BrandMapper.toResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(Long id,
                                     UpdateBrandRequest request) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        brandRepository.findByName(request.getName())
                .ifPresent(existingBrand -> {
                    if (!existingBrand.getId().equals(id)) {
                        throw new ResourceAlreadyExistsException("Brand already exists");
                    }
                });

        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setLogoUrl(request.getLogoUrl());
        brand.setActive(request.getActive());

        Brand updatedBrand = brandRepository.save(brand);

        return BrandMapper.toResponse(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        brandRepository.delete(brand);
    }
}