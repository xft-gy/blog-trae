package com.blog.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("category")
public class CategoryDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String slug;

    private String description;

    private String icon;

    private Integer sortOrder;

    private Integer articleCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
