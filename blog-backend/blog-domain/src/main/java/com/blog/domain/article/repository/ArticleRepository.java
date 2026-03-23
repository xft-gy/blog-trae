package com.blog.domain.article.repository;

import com.blog.domain.article.entity.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findPublishedArticles(int page, int size);

    List<Article> findByCategoryId(Long categoryId, int page, int size);

    List<Article> searchByKeyword(String keyword, int page, int size);

    List<Article> advancedSearch(String keyword, Long categoryId, String sortBy, String sortOrder, int page, int size);

    List<Article> findDraftArticlesByAuthorId(Long authorId, int page, int size);

    List<Article> findArticlesByAuthorId(Long authorId, int page, int size);

    List<Article> findAllArticles(int page, int size);

    void incrementViewCount(Long articleId);

    void incrementLikeCount(Long articleId);

    void decrementLikeCount(Long articleId);

    void incrementCommentCount(Long articleId);

    void decrementCommentCount(Long articleId);
}
