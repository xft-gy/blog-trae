package com.blog.application.article.service;

import com.blog.application.article.dto.CommentDTO;
import com.blog.application.article.dto.CreateCommentRequest;
import com.blog.common.constant.CommonConstants;
import com.blog.common.exception.BusinessException;
import com.blog.domain.article.entity.Comment;
import com.blog.domain.article.repository.ArticleRepository;
import com.blog.domain.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentDTO> getCommentsByArticleId(Long articleId, int page, int size) {
        List<Comment> comments = commentRepository.findByArticleId(articleId, page, size);
        return comments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getRepliesByParentId(Long parentId, int page, int size) {
        List<Comment> comments = commentRepository.findByParentId(parentId, page, size);
        return comments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO createComment(CreateCommentRequest request, Long userId) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(request, comment);
        comment.setUserId(userId);
        comment.setLikeCount(0);
        comment.setStatus(CommonConstants.CommentStatus.APPROVED);
        
        Comment savedComment = commentRepository.save(comment);
        
        articleRepository.incrementCommentCount(request.getArticleId());
        
        return toDTO(savedComment);
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Comment not found"));
        
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("You can only delete your own comments");
        }
        
        commentRepository.deleteById(id);
        
        articleRepository.decrementCommentCount(comment.getArticleId());
    }

    @Transactional
    public void likeComment(Long commentId) {
        commentRepository.incrementLikeCount(commentId);
    }

    @Transactional
    public void unlikeComment(Long commentId) {
        commentRepository.decrementLikeCount(commentId);
    }

    private CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        BeanUtils.copyProperties(comment, dto);
        return dto;
    }
}
