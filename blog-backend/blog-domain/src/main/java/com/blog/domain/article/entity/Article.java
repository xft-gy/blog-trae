package com.blog.domain.article.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Article {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String contentHtml;
    private String coverImage;
    private Long authorId;
    private Long categoryId;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer status;
    private Integer isTop;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime publishedTime;
    
    private List<Long> tagIds;
}
