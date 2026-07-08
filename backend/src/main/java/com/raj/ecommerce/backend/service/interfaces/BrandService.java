
package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.CreateBrandRequest;
import com.raj.ecommerce.backend.dto.request.UpdateBrandRequest;
import com.raj.ecommerce.backend.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {

    BrandResponse createBrand(CreateBrandRequest request);

    List<BrandResponse> getAllBrands();

    BrandResponse getBrandById(Long id);

    BrandResponse updateBrand(Long id, UpdateBrandRequest request);

    void deleteBrand(Long id);

}