package com.blog.interfaces.api;

import com.blog.application.article.dto.StatisticsDTO;
import com.blog.application.article.service.StatisticsService;
import com.blog.common.annotation.RequireAdmin;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Statistics", description = "Statistics APIs")
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Get statistics")
    @GetMapping
    @RequireAdmin
    public Result<StatisticsDTO> getStatistics() {
        StatisticsDTO statistics = statisticsService.getStatistics();
        return Result.success(statistics);
    }
}
