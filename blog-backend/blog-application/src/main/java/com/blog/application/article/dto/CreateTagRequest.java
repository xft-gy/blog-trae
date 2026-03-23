package com.blog.application.article.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class CreateTagRequest {

    @NotBlank(message = "Tag name cannot be empty")
    @Size(max = 50, message = "Tag name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Tag slug cannot be empty")
    @Size(max = 50, message = "Tag slug cannot exceed 50 characters")
    private String slug;

    private String color;
}
