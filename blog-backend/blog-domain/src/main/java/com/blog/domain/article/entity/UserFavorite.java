package com.blog.domain.article.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserFavorite {

    private Long id;
    private Long userId;
    private Long articleId;
    private LocalDateTime createdTime;
}
