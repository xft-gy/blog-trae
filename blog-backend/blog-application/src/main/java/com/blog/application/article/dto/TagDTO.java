package com.blog.application.article.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TagDTO {

    private Long id;
    private String name;
    private String slug;
    private String color;
    private Integer articleCount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
