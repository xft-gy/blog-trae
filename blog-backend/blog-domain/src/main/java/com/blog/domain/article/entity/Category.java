package com.blog.domain.article.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private Integer sortOrder;
    private Integer articleCount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
