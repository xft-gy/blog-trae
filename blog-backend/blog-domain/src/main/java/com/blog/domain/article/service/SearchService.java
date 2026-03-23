package com.blog.domain.article.service;

import com.blog.domain.article.entity.Article;
import java.util.List;

public interface SearchService {
    
    List<Article> search(String keyword, int page, int size);
    
    List<Article> advancedSearch(String keyword, Long categoryId, String sortBy, String sortOrder, int page, int size);
    
    void indexArticle(Article article);
    
    void deleteArticleIndex(Long articleId);
    
    void reindexAllArticles();
}
