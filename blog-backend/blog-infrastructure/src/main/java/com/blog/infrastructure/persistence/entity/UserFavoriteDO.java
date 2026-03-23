package com.blog.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_favorite")
public class UserFavoriteDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long articleId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
