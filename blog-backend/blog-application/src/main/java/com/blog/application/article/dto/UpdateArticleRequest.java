package com.blog.application.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class UpdateArticleRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 500, message = "Summary cannot exceed 500 characters")
    private String summary;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private String coverImage;

    private Long categoryId;

    private List<Long> tagIds;

    private Integer status;
}
