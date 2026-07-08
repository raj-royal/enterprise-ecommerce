


package com.raj.ecommerce.backend.service.interfaces;

import com.raj.ecommerce.backend.dto.request.CreateCategoryRequest;
import com.raj.ecommerce.backend.dto.request.UpdateCategoryRequest;
import com.raj.ecommerce.backend.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);
}