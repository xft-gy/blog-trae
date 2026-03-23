package com.blog.application.article.dto;

import lombok.Data;

@Data
public class SearchResultDTO {
    private ArticleDTO article;
    private String highlightedTitle;
    private String highlightedSummary;
    private String highlightedContent;
}
