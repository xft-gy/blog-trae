package com.blog.domain.article.repository;

import com.blog.domain.article.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    List<Comment> findByArticleId(Long articleId, int page, int size);

    List<Comment> findByParentId(Long parentId, int page, int size);

    void deleteById(Long id);

    void incrementLikeCount(Long commentId);

    void decrementLikeCount(Long commentId);
}
