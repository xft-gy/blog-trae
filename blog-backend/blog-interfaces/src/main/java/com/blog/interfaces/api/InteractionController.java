package com.blog.interfaces.api;

import com.blog.application.article.service.FavoriteService;
import com.blog.application.article.service.LikeService;
import com.blog.common.constant.CommonConstants;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Interaction", description = "Like and Favorite APIs")
@RestController
@RequestMapping("/api/interaction")
@RequiredArgsConstructor
public class InteractionController {

    private final LikeService likeService;
    private final FavoriteService favoriteService;

    @Operation(summary = "Toggle like for article or comment")
    @PostMapping("/like/{targetType}/{targetId}")
    public Result<Void> toggleLike(
            @PathVariable String targetType,
            @PathVariable Long targetId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        likeService.toggleLike(userId, targetType, targetId);
        return Result.success();
    }

    @Operation(summary = "Check if liked")
    @GetMapping("/like/{targetType}/{targetId}")
    public Result<Boolean> checkIfLiked(
            @PathVariable String targetType,
            @PathVariable Long targetId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.success(false);
        }
        boolean liked = likeService.checkIfLiked(userId, targetType, targetId);
        return Result.success(liked);
    }

    @Operation(summary = "Toggle favorite for article")
    @PostMapping("/favorite/{articleId}")
    public Result<Void> toggleFavorite(
            @PathVariable Long articleId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        favoriteService.toggleFavorite(userId, articleId);
        return Result.success();
    }

    @Operation(summary = "Check if favorited")
    @GetMapping("/favorite/{articleId}")
    public Result<Boolean> checkIfFavorited(
            @PathVariable Long articleId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.success(false);
        }
        boolean favorited = favoriteService.checkIfFavorited(userId, articleId);
        return Result.success(favorited);
    }
}
