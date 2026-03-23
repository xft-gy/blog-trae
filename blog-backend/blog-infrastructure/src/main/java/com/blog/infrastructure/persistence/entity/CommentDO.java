package com.blog.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class CommentDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long articleId;

    private Long userId;

    private Long parentId;

    private String content;

    private Integer likeCount;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
