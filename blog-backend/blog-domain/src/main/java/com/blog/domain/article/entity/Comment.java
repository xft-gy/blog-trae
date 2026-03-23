package com.blog.domain.article.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {

    private Long id;
    private Long articleId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
