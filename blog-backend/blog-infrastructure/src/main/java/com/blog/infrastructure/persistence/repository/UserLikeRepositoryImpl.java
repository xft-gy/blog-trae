package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.article.entity.UserLike;
import com.blog.domain.article.repository.UserLikeRepository;
import com.blog.infrastructure.persistence.entity.UserLikeDO;
import com.blog.infrastructure.persistence.mapper.UserLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserLikeRepositoryImpl implements UserLikeRepository {

    private final UserLikeMapper userLikeMapper;

    @Override
    public UserLike save(UserLike userLike) {
        UserLikeDO userLikeDO = new UserLikeDO();
        BeanUtils.copyProperties(userLike, userLikeDO);
        if (userLikeDO.getId() == null) {
            userLikeMapper.insert(userLikeDO);
        } else {
            userLikeMapper.updateById(userLikeDO);
        }
        BeanUtils.copyProperties(userLikeDO, userLike);
        return userLike;
    }

    @Override
    public Optional<UserLike> findByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId) {
        LambdaQueryWrapper<UserLikeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserLikeDO::getUserId, userId);
        wrapper.eq(UserLikeDO::getTargetType, targetType);
        wrapper.eq(UserLikeDO::getTargetId, targetId);
        UserLikeDO userLikeDO = userLikeMapper.selectOne(wrapper);
        if (userLikeDO == null) {
            return Optional.empty();
        }
        UserLike userLike = new UserLike();
        BeanUtils.copyProperties(userLikeDO, userLike);
        return Optional.of(userLike);
    }

    @Override
    public void deleteByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId) {
        LambdaQueryWrapper<UserLikeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserLikeDO::getUserId, userId);
        wrapper.eq(UserLikeDO::getTargetType, targetType);
        wrapper.eq(UserLikeDO::getTargetId, targetId);
        userLikeMapper.delete(wrapper);
    }

    @Override
    public boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, String targetType, Long targetId) {
        LambdaQueryWrapper<UserLikeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserLikeDO::getUserId, userId);
        wrapper.eq(UserLikeDO::getTargetType, targetType);
        wrapper.eq(UserLikeDO::getTargetId, targetId);
        return userLikeMapper.selectCount(wrapper) > 0;
    }
}
