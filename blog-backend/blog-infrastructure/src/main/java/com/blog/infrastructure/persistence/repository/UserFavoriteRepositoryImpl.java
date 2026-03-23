package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.article.entity.UserFavorite;
import com.blog.domain.article.repository.UserFavoriteRepository;
import com.blog.infrastructure.persistence.entity.UserFavoriteDO;
import com.blog.infrastructure.persistence.mapper.UserFavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserFavoriteRepositoryImpl implements UserFavoriteRepository {

    private final UserFavoriteMapper userFavoriteMapper;

    @Override
    public UserFavorite save(UserFavorite userFavorite) {
        UserFavoriteDO userFavoriteDO = new UserFavoriteDO();
        BeanUtils.copyProperties(userFavorite, userFavoriteDO);
        if (userFavoriteDO.getId() == null) {
            userFavoriteMapper.insert(userFavoriteDO);
        } else {
            userFavoriteMapper.updateById(userFavoriteDO);
        }
        BeanUtils.copyProperties(userFavoriteDO, userFavorite);
        return userFavorite;
    }

    @Override
    public Optional<UserFavorite> findByUserIdAndArticleId(Long userId, Long articleId) {
        LambdaQueryWrapper<UserFavoriteDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavoriteDO::getUserId, userId);
        wrapper.eq(UserFavoriteDO::getArticleId, articleId);
        UserFavoriteDO userFavoriteDO = userFavoriteMapper.selectOne(wrapper);
        if (userFavoriteDO == null) {
            return Optional.empty();
        }
        UserFavorite userFavorite = new UserFavorite();
        BeanUtils.copyProperties(userFavoriteDO, userFavorite);
        return Optional.of(userFavorite);
    }

    @Override
    public List<UserFavorite> findByUserId(Long userId) {
        LambdaQueryWrapper<UserFavoriteDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavoriteDO::getUserId, userId);
        wrapper.orderByDesc(UserFavoriteDO::getCreatedTime);
        List<UserFavoriteDO> userFavoriteDOList = userFavoriteMapper.selectList(wrapper);
        return userFavoriteDOList.stream()
                .map(this::toUserFavorite)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUserIdAndArticleId(Long userId, Long articleId) {
        LambdaQueryWrapper<UserFavoriteDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavoriteDO::getUserId, userId);
        wrapper.eq(UserFavoriteDO::getArticleId, articleId);
        userFavoriteMapper.delete(wrapper);
    }

    @Override
    public boolean existsByUserIdAndArticleId(Long userId, Long articleId) {
        LambdaQueryWrapper<UserFavoriteDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavoriteDO::getUserId, userId);
        wrapper.eq(UserFavoriteDO::getArticleId, articleId);
        return userFavoriteMapper.selectCount(wrapper) > 0;
    }

    private UserFavorite toUserFavorite(UserFavoriteDO userFavoriteDO) {
        UserFavorite userFavorite = new UserFavorite();
        BeanUtils.copyProperties(userFavoriteDO, userFavorite);
        return userFavorite;
    }
}
