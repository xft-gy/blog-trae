package com.blog.application.article.service;

import com.blog.application.article.dto.PerformanceMetricsDTO;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PerformanceService {

    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong totalResponseTime = new AtomicLong(0);
    private final long startTime = System.currentTimeMillis();

    public PerformanceMetricsDTO getMetrics() {
        PerformanceMetricsDTO dto = new PerformanceMetricsDTO();
        
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        
        dto.setCpuUsage(osBean.getSystemLoadAverage());
        dto.setMemoryUsage(getMemoryUsage());
        
        Runtime runtime = Runtime.getRuntime();
        dto.setJvmMemoryUsed((runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);
        dto.setJvmMemoryMax(runtime.maxMemory() / 1024 / 1024);
        
        dto.setActiveThreads(threadBean.getThreadCount());
        
        long uptime = System.currentTimeMillis() - startTime;
        dto.setUptimeSeconds(uptime / 1000);
        
        dto.setTotalRequests(totalRequests.get());
        
        if (totalRequests.get() > 0) {
            dto.setAverageResponseTime((double) totalResponseTime.get() / totalRequests.get());
        } else {
            dto.setAverageResponseTime(0.0);
        }
        
        if (uptime > 0) {
            dto.setRequestsPerSecond(totalRequests.get() * 1000L / uptime);
        } else {
            dto.setRequestsPerSecond(0L);
        }
        
        return dto;
    }

    private Double getMemoryUsage() {
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            java.lang.reflect.Method method = osBean.getClass().getMethod("getSystemLoadAverage");
            Object result = method.invoke(osBean);
            if (result instanceof Double) {
                return (Double) result;
            }
        } catch (Exception e) {
            return 0.0;
        }
        return 0.0;
    }

    public void recordRequest(long responseTimeMs) {
        totalRequests.incrementAndGet();
        totalResponseTime.addAndGet(responseTimeMs);
    }
}
