package com.blog.application.article.service;

import com.blog.application.article.dto.StatisticsDTO;
import com.blog.infrastructure.persistence.entity.ArticleDO;
import com.blog.infrastructure.persistence.entity.CommentDO;
import com.blog.infrastructure.persistence.entity.UserDO;
import com.blog.infrastructure.persistence.mapper.ArticleMapper;
import com.blog.infrastructure.persistence.mapper.CommentMapper;
import com.blog.infrastructure.persistence.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

    public StatisticsDTO getStatistics() {
        StatisticsDTO dto = new StatisticsDTO();
        
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        
        dto.setTotalUsers(userMapper.selectCount(null));
        dto.setTotalArticles(articleMapper.selectCount(null));
        dto.setTotalComments(commentMapper.selectCount(null));
        
        Long totalViews = articleMapper.selectList(null).stream()
                .mapToLong(ArticleDO::getViewCount)
                .sum();
        dto.setTotalViews(totalViews);
        
        LambdaQueryWrapper<UserDO> userQuery = new LambdaQueryWrapper<>();
        userQuery.ge(UserDO::getCreatedTime, startOfDay);
        userQuery.le(UserDO::getCreatedTime, endOfDay);
        dto.setTodayNewUsers(userMapper.selectCount(userQuery));
        
        LambdaQueryWrapper<ArticleDO> articleQuery = new LambdaQueryWrapper<>();
        articleQuery.ge(ArticleDO::getCreatedTime, startOfDay);
        articleQuery.le(ArticleDO::getCreatedTime, endOfDay);
        dto.setTodayNewArticles(articleMapper.selectCount(articleQuery));
        
        LambdaQueryWrapper<CommentDO> commentQuery = new LambdaQueryWrapper<>();
        commentQuery.ge(CommentDO::getCreatedTime, startOfDay);
        commentQuery.le(CommentDO::getCreatedTime, endOfDay);
        dto.setTodayNewComments(commentMapper.selectCount(commentQuery));
        
        dto.setTodayTotalViews(0L);
        
        return dto;
    }
}
