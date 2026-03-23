package com.blog.application.article.service;

import com.blog.common.constant.CommonConstants;
import com.blog.common.exception.BusinessException;
import com.blog.domain.article.entity.UserLike;
import com.blog.domain.article.repository.ArticleRepository;
import com.blog.domain.article.repository.CommentRepository;
import com.blog.domain.article.repository.UserLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserLikeRepository userLikeRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void toggleLike(Long userId, String targetType, Long targetId) {
        boolean exists = userLikeRepository.existsByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);
        
        if (exists) {
            userLikeRepository.deleteByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);
            
            if ("ARTICLE".equals(targetType)) {
                articleRepository.decrementLikeCount(targetId);
            } else if ("COMMENT".equals(targetType)) {
                commentRepository.decrementLikeCount(targetId);
            }
        } else {
            UserLike userLike = new UserLike();
            userLike.setUserId(userId);
            userLike.setTargetType(targetType);
            userLike.setTargetId(targetId);
            userLikeRepository.save(userLike);
            
            if ("ARTICLE".equals(targetType)) {
                articleRepository.incrementLikeCount(targetId);
            } else if ("COMMENT".equals(targetType)) {
                commentRepository.incrementLikeCount(targetId);
            }
        }
    }

    public boolean checkIfLiked(Long userId, String targetType, Long targetId) {
        return userLikeRepository.existsByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);
    }
}
