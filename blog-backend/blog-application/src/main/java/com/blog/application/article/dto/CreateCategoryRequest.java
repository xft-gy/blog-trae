package com.blog.application.article.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class CreateCategoryRequest {

    @NotBlank(message = "Category name cannot be empty")
    @Size(max = 50, message = "Category name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Category slug cannot be empty")
    @Size(max = 50, message = "Category slug cannot exceed 50 characters")
    private String slug;

    @Size(max = 200, message = "Category description cannot exceed 200 characters")
    private String description;

    private String icon;

    private Integer sortOrder;
}
