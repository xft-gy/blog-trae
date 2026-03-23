package com.blog.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("article")
public class ArticleDO {

    @TableId(type = IdType.ASSIGN_ID)
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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    private LocalDateTime publishedTime;
}
