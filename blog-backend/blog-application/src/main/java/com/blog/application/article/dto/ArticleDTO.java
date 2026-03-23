package com.blog.application.article.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDTO {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String contentHtml;
    private String coverImage;
    private Long authorId;
    private String authorName;
    private Long categoryId;
    private String categoryName;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer status;
    private Integer isTop;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime publishedTime;
    private List<Long> tagIds;
    private List<String> tagNames;
}
