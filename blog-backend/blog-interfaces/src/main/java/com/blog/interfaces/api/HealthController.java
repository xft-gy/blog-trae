package com.blog.interfaces.api;

import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Health", description = "Health check APIs")
@RestController
@RequestMapping("/api")
public class HealthController {

    @Operation(summary = "Health check")
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("service", "Blog Backend");
        data.put("version", "1.0.0");
        return Result.success(data);
    }
}
