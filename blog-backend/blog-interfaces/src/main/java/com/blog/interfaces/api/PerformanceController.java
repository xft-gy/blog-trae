package com.blog.interfaces.api;

import com.blog.application.article.dto.PerformanceMetricsDTO;
import com.blog.application.article.service.PerformanceService;
import com.blog.common.annotation.RequireAdmin;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Performance", description = "Performance monitoring APIs")
@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @Operation(summary = "Get performance metrics")
    @GetMapping
    @RequireAdmin
    public Result<PerformanceMetricsDTO> getMetrics() {
        PerformanceMetricsDTO metrics = performanceService.getMetrics();
        return Result.success(metrics);
    }
}
