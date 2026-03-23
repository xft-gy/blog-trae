package com.blog.application.article.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;
    private Long articleId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
