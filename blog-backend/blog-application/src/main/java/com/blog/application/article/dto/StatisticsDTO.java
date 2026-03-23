package com.blog.application.article.dto;

import lombok.Data;

@Data
public class StatisticsDTO {
    private Long totalUsers;
    private Long totalArticles;
    private Long totalComments;
    private Long totalViews;
    private Long todayNewUsers;
    private Long todayNewArticles;
    private Long todayNewComments;
    private Long todayTotalViews;
}
