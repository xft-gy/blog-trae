package com.blog.interfaces.api;

import com.blog.application.article.dto.CategoryDTO;
import com.blog.application.article.dto.CreateCategoryRequest;
import com.blog.application.article.service.CategoryService;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Category management APIs")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @GetMapping
    public Result<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public Result<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return Result.success(category);
    }

    @Operation(summary = "Get category by slug")
    @GetMapping("/slug/{slug}")
    public Result<CategoryDTO> getCategoryBySlug(@PathVariable String slug) {
        CategoryDTO category = categoryService.getCategoryBySlug(slug);
        return Result.success(category);
    }

    @Operation(summary = "Create category")
    @PostMapping
    public Result<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryDTO category = categoryService.createCategory(request);
        return Result.success(category);
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public Result<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryRequest request) {
        CategoryDTO category = categoryService.updateCategory(id, request);
        return Result.success(category);
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
