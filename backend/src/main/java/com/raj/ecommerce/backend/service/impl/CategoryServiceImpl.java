package com.raj.ecommerce.backend.service.impl;

import com.raj.ecommerce.backend.dto.request.CreateCategoryRequest;
import com.raj.ecommerce.backend.dto.request.UpdateCategoryRequest;
import com.raj.ecommerce.backend.dto.response.CategoryResponse;
import com.raj.ecommerce.backend.entity.Category;
import com.raj.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.raj.ecommerce.backend.exception.ResourceNotFoundException;
import com.raj.ecommerce.backend.mapper.CategoryMapper;
import com.raj.ecommerce.backend.repository.CategoryRepository;
import com.raj.ecommerce.backend.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Category already exists");
        }

        Category category = CategoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id,
                                           UpdateCategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        categoryRepository.findByName(request.getName())
                .ifPresent(existingCategory -> {
                    if (!existingCategory.getId().equals(id)) {
                        throw new ResourceAlreadyExistsException("Category already exists");
                    }
                });

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        category.setActive(request.getActive());

        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }
}