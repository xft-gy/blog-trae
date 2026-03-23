package com.blog.domain.article.repository;

import com.blog.domain.article.entity.UserLike;
import java.util.Optional;

public interface UserLikeRepository {

    UserLike save(UserLike userLike);

    Optional<UserLike> findByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId);

    void deleteByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId);

    boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId);
}
