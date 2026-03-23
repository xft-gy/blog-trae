package com.blog.domain.article.repository;

import com.blog.domain.article.entity.UserFavorite;
import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository {

    UserFavorite save(UserFavorite userFavorite);

    Optional<UserFavorite> findByUserIdAndArticleId(Long userId, Long articleId);

    List<UserFavorite> findByUserId(Long userId);

    void deleteByUserIdAndArticleId(Long userId, Long articleId);

    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
