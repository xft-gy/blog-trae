package com.blog.application.article.dto;

import lombok.Data;

@Data
public class PerformanceMetricsDTO {
    private Double cpuUsage;
    private Double memoryUsage;
    private Long jvmMemoryUsed;
    private Long jvmMemoryMax;
    private Integer activeThreads;
    private Long uptimeSeconds;
    private Long totalRequests;
    private Double averageResponseTime;
    private Long requestsPerSecond;
}
