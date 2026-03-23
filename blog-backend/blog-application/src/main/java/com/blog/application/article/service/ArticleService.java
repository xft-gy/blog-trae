package com.blog.application.article.service;

import com.blog.application.article.dto.ArticleDTO;
import com.blog.application.article.dto.CreateArticleRequest;
import com.blog.application.article.dto.SearchArticleRequest;
import com.blog.application.article.dto.SearchResultDTO;
import com.blog.application.article.dto.UpdateArticleRequest;
import com.blog.common.constant.CommonConstants;
import com.blog.common.exception.BusinessException;
import com.blog.domain.article.entity.Article;
import com.blog.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Article not found"));
        
        articleRepository.incrementViewCount(id);
        
        return toDTO(article);
    }

    public List<ArticleDTO> getPublishedArticles(int page, int size) {
        List<Article> articles = articleRepository.findPublishedArticles(page, size);
        return articles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getArticlesByCategory(Long categoryId, int page, int size) {
        List<Article> articles = articleRepository.findByCategoryId(categoryId, page, size);
        return articles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> searchArticles(String keyword, int page, int size) {
        List<Article> articles = articleRepository.searchByKeyword(keyword, page, size);
        return articles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SearchResultDTO> advancedSearch(SearchArticleRequest request) {
        List<Article> articles = articleRepository.advancedSearch(
                request.getKeyword(),
                request.getCategoryId(),
                request.getSortBy(),
                request.getSortOrder(),
                request.getPage(),
                request.getSize()
        );
        
        return articles.stream()
                .map(article -> toSearchResultDTO(article, request.getKeyword()))
                .collect(Collectors.toList());
    }

    private SearchResultDTO toSearchResultDTO(Article article, String keyword) {
        SearchResultDTO result = new SearchResultDTO();
        result.setArticle(toDTO(article));
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            result.setHighlightedTitle(highlightText(article.getTitle(), keyword));
            result.setHighlightedSummary(highlightText(article.getSummary(), keyword));
            result.setHighlightedContent(highlightText(article.getContent(), keyword));
        } else {
            result.setHighlightedTitle(article.getTitle());
            result.setHighlightedSummary(article.getSummary());
            result.setHighlightedContent(article.getContent());
        }
        
        return result;
    }

    private String highlightText(String text, String keyword) {
        if (text == null || keyword == null || keyword.trim().isEmpty()) {
            return text;
        }
        
        String lowerText = text.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();
        int index = lowerText.indexOf(lowerKeyword);
        
        if (index == -1) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;
        
        while (index != -1) {
            result.append(text, lastIndex, index);
            result.append("<em>");
            result.append(text, index, index + keyword.length());
            result.append("</em>");
            lastIndex = index + keyword.length();
            index = lowerText.indexOf(lowerKeyword, lastIndex);
        }
        
        result.append(text.substring(lastIndex));
        return result.toString();
    }

    public List<ArticleDTO> getDraftArticles(Long authorId, int page, int size) {
        List<Article> articles = articleRepository.findDraftArticlesByAuthorId(authorId, page, size);
        return articles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getMyArticles(Long authorId, int page, int size) {
        List<Article> articles = articleRepository.findArticlesByAuthorId(authorId, page, size);
        return articles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleDTO createArticle(CreateArticleRequest request, Long authorId) {
        Article article = new Article();
        BeanUtils.copyProperties(request, article);
        article.setAuthorId(authorId);
        article.setViewCount(0);
        article.setLikeCount(0);
        article.setCommentCount(0);
        article.setIsTop(0);
        
        if (request.getStatus() == null) {
            article.setStatus(CommonConstants.ArticleStatus.DRAFT);
        }
        
        if (CommonConstants.ArticleStatus.PUBLISHED.equals(article.getStatus())) {
            article.setPublishedTime(LocalDateTime.now());
        }
        
        Article savedArticle = articleRepository.save(article);
        return toDTO(savedArticle);
    }

    @Transactional
    public ArticleDTO updateArticle(Long id, UpdateArticleRequest request, Long userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Article not found"));
        
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException("You can only update your own articles");
        }
        
        BeanUtils.copyProperties(request, article, "id", "authorId", "viewCount", "likeCount", "commentCount", "createdTime");
        
        if (CommonConstants.ArticleStatus.PUBLISHED.equals(request.getStatus()) 
                && article.getPublishedTime() == null) {
            article.setPublishedTime(LocalDateTime.now());
        }
        
        Article updatedArticle = articleRepository.save(article);
        return toDTO(updatedArticle);
    }

    @Transactional
    public void deleteArticle(Long id, Long userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Article not found"));
        
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException("You can only delete your own articles");
        }
        
        article.setStatus(CommonConstants.ArticleStatus.OFFLINE);
        articleRepository.save(article);
    }

    @Transactional
    public void publishArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Article not found"));
        
        article.setStatus(CommonConstants.ArticleStatus.PUBLISHED);
        if (article.getPublishedTime() == null) {
            article.setPublishedTime(LocalDateTime.now());
        }
        articleRepository.save(article);
    }

    @Transactional
    public void offlineArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Article not found"));
        
        article.setStatus(CommonConstants.ArticleStatus.OFFLINE);
        articleRepository.save(article);
    }

    public List<ArticleDTO> getAllArticles(int page, int size) {
        List<Article> articles = articleRepository.findAllArticles(page, size);
        return articles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ArticleDTO toDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        BeanUtils.copyProperties(article, dto);
        return dto;
    }
}
