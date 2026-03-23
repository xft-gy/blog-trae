package com.blog.interfaces.api;

import com.blog.application.article.dto.ArticleDTO;
import com.blog.application.article.dto.CreateArticleRequest;
import com.blog.application.article.dto.SearchArticleRequest;
import com.blog.application.article.dto.SearchResultDTO;
import com.blog.application.article.dto.UpdateArticleRequest;
import com.blog.application.article.service.ArticleService;
import com.blog.common.annotation.RequireAdmin;
import com.blog.common.constant.CommonConstants;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article", description = "Article management APIs")
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Get article by ID")
    @GetMapping("/{id}")
    public Result<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        return Result.success(article);
    }

    @Operation(summary = "Get published articles")
    @GetMapping
    public Result<List<ArticleDTO>> getPublishedArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ArticleDTO> articles = articleService.getPublishedArticles(page, size);
        return Result.success(articles);
    }

    @Operation(summary = "Get articles by category")
    @GetMapping("/category/{categoryId}")
    public Result<List<ArticleDTO>> getArticlesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(categoryId, page, size);
        return Result.success(articles);
    }

    @Operation(summary = "Search articles")
    @GetMapping("/search")
    public Result<List<ArticleDTO>> searchArticles(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ArticleDTO> articles = articleService.searchArticles(keyword, page, size);
        return Result.success(articles);
    }

    @Operation(summary = "Advanced search articles")
    @GetMapping("/search/advanced")
    public Result<List<SearchResultDTO>> advancedSearchArticles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        SearchArticleRequest request = new SearchArticleRequest();
        request.setKeyword(keyword);
        request.setCategoryId(categoryId);
        request.setSortBy(sortBy);
        request.setSortOrder(sortOrder);
        request.setPage(page);
        request.setSize(size);
        
        List<SearchResultDTO> results = articleService.advancedSearch(request);
        return Result.success(results);
    }

    @Operation(summary = "Create article")
    @PostMapping
    public Result<ArticleDTO> createArticle(
            @Valid @RequestBody CreateArticleRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        ArticleDTO article = articleService.createArticle(request, userId);
        return Result.success(article);
    }

    @Operation(summary = "Update article")
    @PutMapping("/{id}")
    public Result<ArticleDTO> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateArticleRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        ArticleDTO article = articleService.updateArticle(id, request, userId);
        return Result.success(article);
    }

    @Operation(summary = "Delete article")
    @DeleteMapping("/{id}")
    public Result<Void> deleteArticle(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        articleService.deleteArticle(id, userId);
        return Result.success();
    }

    @Operation(summary = "Get my draft articles")
    @GetMapping("/my/drafts")
    public Result<List<ArticleDTO>> getMyDraftArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        List<ArticleDTO> articles = articleService.getDraftArticles(userId, page, size);
        return Result.success(articles);
    }

    @Operation(summary = "Get my articles")
    @GetMapping("/my")
    public Result<List<ArticleDTO>> getMyArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        List<ArticleDTO> articles = articleService.getMyArticles(userId, page, size);
        return Result.success(articles);
    }

    @Operation(summary = "Save draft")
    @PostMapping("/draft")
    public Result<ArticleDTO> saveDraft(
            @Valid @RequestBody CreateArticleRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            return Result.error(401, "Please login first");
        }
        request.setStatus(CommonConstants.ArticleStatus.DRAFT);
        ArticleDTO article = articleService.createArticle(request, userId);
        return Result.success(article);
    }

    @Operation(summary = "Get all articles (Admin)")
    @GetMapping("/admin/all")
    @RequireAdmin
    public Result<List<ArticleDTO>> getAllArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ArticleDTO> articles = articleService.getAllArticles(page, size);
        return Result.success(articles);
    }

    @Operation(summary = "Publish article (Admin)")
    @PutMapping("/admin/{id}/publish")
    @RequireAdmin
    public Result<Void> publishArticle(@PathVariable Long id) {
        articleService.publishArticle(id);
        return Result.success();
    }

    @Operation(summary = "Offline article (Admin)")
    @PutMapping("/admin/{id}/offline")
    @RequireAdmin
    public Result<Void> offlineArticle(@PathVariable Long id) {
        articleService.offlineArticle(id);
        return Result.success();
    }
}
