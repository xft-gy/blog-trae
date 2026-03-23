package com.blog.application.article.service;

import com.blog.application.article.dto.CategoryDTO;
import com.blog.application.article.dto.CreateCategoryRequest;
import com.blog.common.exception.BusinessException;
import com.blog.domain.article.entity.Category;
import com.blog.domain.article.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found"));
        return toDTO(category);
    }

    public CategoryDTO getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new BusinessException("Category not found"));
        return toDTO(category);
    }

    @Transactional
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        category.setArticleCount(0);
        if (category.getSortOrder() == null) {
            category.setSortOrder(0);
        }
        Category savedCategory = categoryRepository.save(category);
        return toDTO(savedCategory);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CreateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found"));
        BeanUtils.copyProperties(request, category, "id", "articleCount", "createdTime");
        Category updatedCategory = categoryRepository.save(category);
        return toDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found"));
        categoryRepository.deleteById(id);
    }

    private CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }
}
