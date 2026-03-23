package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.constant.CommonConstants;
import com.blog.domain.article.entity.Article;
import com.blog.domain.article.repository.ArticleRepository;
import com.blog.infrastructure.persistence.entity.ArticleDO;
import com.blog.infrastructure.persistence.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleMapper articleMapper;

    @Override
    public Article save(Article article) {
        ArticleDO articleDO = new ArticleDO();
        BeanUtils.copyProperties(article, articleDO);
        if (articleDO.getId() == null) {
            articleMapper.insert(articleDO);
        } else {
            articleMapper.updateById(articleDO);
        }
        BeanUtils.copyProperties(articleDO, article);
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        ArticleDO articleDO = articleMapper.selectById(id);
        if (articleDO == null) {
            return Optional.empty();
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleDO, article);
        return Optional.of(article);
    }

    @Override
    public List<Article> findPublishedArticles(int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleDO::getStatus, CommonConstants.ArticleStatus.PUBLISHED);
        wrapper.orderByDesc(ArticleDO::getIsTop);
        wrapper.orderByDesc(ArticleDO::getPublishedTime);
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> findByCategoryId(Long categoryId, int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleDO::getCategoryId, categoryId);
        wrapper.eq(ArticleDO::getStatus, CommonConstants.ArticleStatus.PUBLISHED);
        wrapper.orderByDesc(ArticleDO::getPublishedTime);
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> searchByKeyword(String keyword, int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w
                .like(ArticleDO::getTitle, keyword)
                .or()
                .like(ArticleDO::getSummary, keyword)
                .or()
                .like(ArticleDO::getContent, keyword)
        );
        wrapper.eq(ArticleDO::getStatus, CommonConstants.ArticleStatus.PUBLISHED);
        wrapper.orderByDesc(ArticleDO::getPublishedTime);
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> advancedSearch(String keyword, Long categoryId, String sortBy, String sortOrder, int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w
                    .like(ArticleDO::getTitle, keyword)
                    .or()
                    .like(ArticleDO::getSummary, keyword)
                    .or()
                    .like(ArticleDO::getContent, keyword)
            );
        }
        
        if (categoryId != null) {
            wrapper.eq(ArticleDO::getCategoryId, categoryId);
        }
        
        wrapper.eq(ArticleDO::getStatus, CommonConstants.ArticleStatus.PUBLISHED);
        
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            boolean isAsc = "asc".equalsIgnoreCase(sortOrder);
            String sortByLower = sortBy.toLowerCase().replace("_", "");
            switch (sortByLower) {
                case "viewcount":
                    if (isAsc) {
                        wrapper.orderByAsc(ArticleDO::getViewCount);
                    } else {
                        wrapper.orderByDesc(ArticleDO::getViewCount);
                    }
                    break;
                case "likecount":
                    if (isAsc) {
                        wrapper.orderByAsc(ArticleDO::getLikeCount);
                    } else {
                        wrapper.orderByDesc(ArticleDO::getLikeCount);
                    }
                    break;
                case "commentcount":
                    if (isAsc) {
                        wrapper.orderByAsc(ArticleDO::getCommentCount);
                    } else {
                        wrapper.orderByDesc(ArticleDO::getCommentCount);
                    }
                    break;
                case "time":
                default:
                    if (isAsc) {
                        wrapper.orderByAsc(ArticleDO::getPublishedTime);
                    } else {
                        wrapper.orderByDesc(ArticleDO::getPublishedTime);
                    }
                    break;
            }
        } else {
            wrapper.orderByDesc(ArticleDO::getIsTop);
            wrapper.orderByDesc(ArticleDO::getPublishedTime);
        }
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> findDraftArticlesByAuthorId(Long authorId, int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleDO::getAuthorId, authorId);
        wrapper.eq(ArticleDO::getStatus, CommonConstants.ArticleStatus.DRAFT);
        wrapper.orderByDesc(ArticleDO::getUpdatedTime);
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> findArticlesByAuthorId(Long authorId, int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleDO::getAuthorId, authorId);
        wrapper.orderByDesc(ArticleDO::getUpdatedTime);
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> findAllArticles(int page, int size) {
        LambdaQueryWrapper<ArticleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ArticleDO::getUpdatedTime);
        
        Page<ArticleDO> pageResult = articleMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toArticle)
                .collect(Collectors.toList());
    }

    @Override
    public void incrementViewCount(Long articleId) {
        ArticleDO articleDO = articleMapper.selectById(articleId);
        if (articleDO != null) {
            articleDO.setViewCount(articleDO.getViewCount() + 1);
            articleMapper.updateById(articleDO);
        }
    }

    @Override
    public void incrementLikeCount(Long articleId) {
        ArticleDO articleDO = articleMapper.selectById(articleId);
        if (articleDO != null) {
            articleDO.setLikeCount(articleDO.getLikeCount() + 1);
            articleMapper.updateById(articleDO);
        }
    }

    @Override
    public void decrementLikeCount(Long articleId) {
        ArticleDO articleDO = articleMapper.selectById(articleId);
        if (articleDO != null && articleDO.getLikeCount() > 0) {
            articleDO.setLikeCount(articleDO.getLikeCount() - 1);
            articleMapper.updateById(articleDO);
        }
    }

    @Override
    public void incrementCommentCount(Long articleId) {
        ArticleDO articleDO = articleMapper.selectById(articleId);
        if (articleDO != null) {
            articleDO.setCommentCount(articleDO.getCommentCount() + 1);
            articleMapper.updateById(articleDO);
        }
    }

    @Override
    public void decrementCommentCount(Long articleId) {
        ArticleDO articleDO = articleMapper.selectById(articleId);
        if (articleDO != null && articleDO.getCommentCount() > 0) {
            articleDO.setCommentCount(articleDO.getCommentCount() - 1);
            articleMapper.updateById(articleDO);
        }
    }

    private Article toArticle(ArticleDO articleDO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDO, article);
        return article;
    }
}
