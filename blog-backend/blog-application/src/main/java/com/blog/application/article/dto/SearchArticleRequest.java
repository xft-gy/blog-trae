package com.blog.application.article.dto;

import lombok.Data;

@Data
public class SearchArticleRequest {
    private String keyword;
    private Long categoryId;
    private String sortBy;
    private String sortOrder;
    private int page = 1;
    private int size = 10;
}
