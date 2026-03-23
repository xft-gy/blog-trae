package com.blog.application.article.service;

import com.blog.common.exception.BusinessException;
import com.blog.domain.article.entity.UserFavorite;
import com.blog.domain.article.repository.UserFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;

    @Transactional
    public void toggleFavorite(Long userId, Long articleId) {
        boolean exists = userFavoriteRepository.existsByUserIdAndArticleId(userId, articleId);
        
        if (exists) {
            userFavoriteRepository.deleteByUserIdAndArticleId(userId, articleId);
        } else {
            UserFavorite userFavorite = new UserFavorite();
            userFavorite.setUserId(userId);
            userFavorite.setArticleId(articleId);
            userFavoriteRepository.save(userFavorite);
        }
    }

    public boolean checkIfFavorited(Long userId, Long articleId) {
        return userFavoriteRepository.existsByUserIdAndArticleId(userId, articleId);
    }

    public List<UserFavorite> getUserFavorites(Long userId) {
        return userFavoriteRepository.findByUserId(userId);
    }
}
