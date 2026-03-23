package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.constant.CommonConstants;
import com.blog.domain.article.entity.Comment;
import com.blog.domain.article.repository.CommentRepository;
import com.blog.infrastructure.persistence.entity.CommentDO;
import com.blog.infrastructure.persistence.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentMapper commentMapper;

    @Override
    public Comment save(Comment comment) {
        CommentDO commentDO = new CommentDO();
        BeanUtils.copyProperties(comment, commentDO);
        if (commentDO.getId() == null) {
            commentMapper.insert(commentDO);
        } else {
            commentMapper.updateById(commentDO);
        }
        BeanUtils.copyProperties(commentDO, comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        CommentDO commentDO = commentMapper.selectById(id);
        if (commentDO == null) {
            return Optional.empty();
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDO, comment);
        return Optional.of(comment);
    }

    @Override
    public List<Comment> findByArticleId(Long articleId, int page, int size) {
        LambdaQueryWrapper<CommentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentDO::getArticleId, articleId);
        wrapper.eq(CommentDO::getStatus, CommonConstants.CommentStatus.APPROVED);
        wrapper.isNull(CommentDO::getParentId);
        wrapper.orderByDesc(CommentDO::getCreatedTime);
        
        Page<CommentDO> pageResult = commentMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toComment)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByParentId(Long parentId, int page, int size) {
        LambdaQueryWrapper<CommentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentDO::getParentId, parentId);
        wrapper.eq(CommentDO::getStatus, CommonConstants.CommentStatus.APPROVED);
        wrapper.orderByDesc(CommentDO::getCreatedTime);
        
        Page<CommentDO> pageResult = commentMapper.selectPage(new Page<>(page, size), wrapper);
        return pageResult.getRecords().stream()
                .map(this::toComment)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    public void incrementLikeCount(Long commentId) {
        CommentDO commentDO = commentMapper.selectById(commentId);
        if (commentDO != null) {
            commentDO.setLikeCount(commentDO.getLikeCount() + 1);
            commentMapper.updateById(commentDO);
        }
    }

    @Override
    public void decrementLikeCount(Long commentId) {
        CommentDO commentDO = commentMapper.selectById(commentId);
        if (commentDO != null && commentDO.getLikeCount() > 0) {
            commentDO.setLikeCount(commentDO.getLikeCount() - 1);
            commentMapper.updateById(commentDO);
        }
    }

    private Comment toComment(CommentDO commentDO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDO, comment);
        return comment;
    }
}
