package com.blog.interfaces.api;

import com.blog.application.article.dto.CommentDTO;
import com.blog.application.article.dto.CreateCommentRequest;
import com.blog.application.article.service.CommentService;
import com.blog.common.constant.CommonConstants;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "Comment management APIs")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Get comments by article ID")
    @GetMapping("/article/{articleId}")
    public Result<List<CommentDTO>> getCommentsByArticleId(
            @PathVariable Long articleId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<CommentDTO> comments = commentService.getCommentsByArticleId(articleId, page, size);
        return Result.success(comments);
    }

    @Operation(summary = "Get replies by parent comment ID")
    @GetMapping("/parent/{parentId}")
    public Result<List<CommentDTO>> getRepliesByParentId(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<CommentDTO> comments = commentService.getRepliesByParentId(parentId, page, size);
        return Result.success(comments);
    }

    @Operation(summary = "Create comment")
    @PostMapping
    public Result<CommentDTO> createComment(
            @Valid @RequestBody CreateCommentRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        CommentDTO comment = commentService.createComment(request, userId);
        return Result.success(comment);
    }

    @Operation(summary = "Delete comment")
    @DeleteMapping("/{id}")
    public Result<Void> deleteComment(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        commentService.deleteComment(id, userId);
        return Result.success();
    }

    @Operation(summary = "Like comment")
    @PostMapping("/{id}/like")
    public Result<Void> likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
        return Result.success();
    }

    @Operation(summary = "Unlike comment")
    @PostMapping("/{id}/unlike")
    public Result<Void> unlikeComment(@PathVariable Long id) {
        commentService.unlikeComment(id);
        return Result.success();
    }
}
