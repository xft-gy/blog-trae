package com.blog.infrastructure.search;

import com.blog.domain.article.entity.Article;
import com.blog.domain.article.repository.ArticleRepository;
import com.blog.domain.article.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MySqlSearchService implements SearchService {
    
    private final ArticleRepository articleRepository;
    
    @Override
    public List<Article> search(String keyword, int page, int size) {
        return articleRepository.searchByKeyword(keyword, page, size);
    }
    
    @Override
    public List<Article> advancedSearch(String keyword, Long categoryId, String sortBy, String sortOrder, int page, int size) {
        return articleRepository.advancedSearch(keyword, categoryId, sortBy, sortOrder, page, size);
    }
    
    @Override
    public void indexArticle(Article article) {
        log.info("MySQL search doesn't require explicit indexing: articleId={}", article.getId());
    }
    
    @Override
    public void deleteArticleIndex(Long articleId) {
        log.info("MySQL search doesn't require explicit index deletion: articleId={}", articleId);
    }
    
    @Override
    public void reindexAllArticles() {
        log.info("MySQL search doesn't require reindexing");
    }
}
