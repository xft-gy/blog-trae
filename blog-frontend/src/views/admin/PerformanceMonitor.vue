<template>
  <div class="performance-monitor">
    <el-card class="monitor-card">
      <template #header>
        <div class="card-header">
          <span>性能监控</span>
          <el-button type="primary" @click="loadMetrics" :loading="loading">
            刷新
          </el-button>
        </div>
      </template>

      <el-row :gutter="20">
        <el-col :span="6">
          <div class="metric-item">
            <div class="metric-icon cpu-icon">
              <el-icon><Cpu /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ formatPercentage(metrics?.cpuUsage) }}</div>
              <div class="metric-label">CPU 使用率</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="metric-item">
            <div class="metric-icon memory-icon">
              <el-icon><Odometer /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ formatMemory(metrics?.jvmMemoryUsed) }}</div>
              <div class="metric-label">已用内存</div>
              <div class="metric-sub">{{ formatMemory(metrics?.jvmMemoryMax) }} 最大</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="metric-item">
            <div class="metric-icon thread-icon">
              <el-icon><Connection /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ metrics?.activeThreads || 0 }}</div>
              <div class="metric-label">活跃线程</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="metric-item">
            <div class="metric-icon uptime-icon">
              <el-icon><Timer /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ formatUptime(metrics?.uptimeSeconds) }}</div>
              <div class="metric-label">运行时间</div>
            </div>
          </div>
        </el-col>
      </el-row>

      <el-divider />

      <el-row :gutter="20">
        <el-col :span="8">
          <div class="metric-item">
            <div class="metric-icon request-icon">
              <el-icon><Document /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ formatNumber(metrics?.totalRequests) }}</div>
              <div class="metric-label">总请求数</div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="metric-item">
            <div class="metric-icon response-icon">
              <el-icon><Clock /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ formatMs(metrics?.averageResponseTime) }}</div>
              <div class="metric-label">平均响应时间</div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="metric-item">
            <div class="metric-icon rps-icon">
              <el-icon><TrendCharts /></el-icon>
            </div>
            <div class="metric-content">
              <div class="metric-value">{{ metrics?.requestsPerSecond || 0 }}</div>
              <div class="metric-label">请求/秒</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Cpu, Odometer, Connection, Timer, Document, Clock, TrendCharts } from '@element-plus/icons-vue'
import { getPerformanceMetrics, type PerformanceMetricsDTO } from '@/api/performance'

const loading = ref(false)
const metrics = ref<PerformanceMetricsDTO | null>(null)
let refreshTimer: number | null = null

const loadMetrics = async () => {
  loading.value = true
  try {
    const res = await getPerformanceMetrics()
    metrics.value = res.data
  } catch (error) {
    ElMessage.error('加载性能指标失败')
  } finally {
    loading.value = false
  }
}

const formatPercentage = (value?: number) => {
  if (value === undefined) return '0%'
  return (value * 100).toFixed(1) + '%'
}

const formatMemory = (value?: number) => {
  if (value === undefined) return '0 MB'
  return value + ' MB'
}

const formatNumber = (value?: number) => {
  if (value === undefined) return '0'
  return value.toLocaleString()
}

const formatMs = (value?: number) => {
  if (value === undefined) return '0 ms'
  return value.toFixed(2) + ' ms'
}

const formatUptime = (seconds?: number) => {
  if (seconds === undefined) return '0s'
  const h = Math.floor(seconds / 3600)
  const m = Math.floor((seconds % 3600) / 60)
  const s = seconds % 60
  if (h > 0) {
    return `${h}h ${m}m ${s}s`
  } else if (m > 0) {
    return `${m}m ${s}s`
  } else {
    return `${s}s`
  }
}

onMounted(() => {
  loadMetrics()
  refreshTimer = window.setInterval(loadMetrics, 5000)
})

onUnmounted(() => {
  if (refreshTimer !== null) {
    clearInterval(refreshTimer)
  }
})
</script>

<style scoped lang="scss">
.performance-monitor {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 18px;
    font-weight: 600;
  }

  .metric-item {
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 24px;
    background: #f8f9fa;
    border-radius: 8px;
    margin-bottom: 20px;

    .metric-icon {
      width: 56px;
      height: 56px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 28px;
      color: #fff;

      &.cpu-icon {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      }

      &.memory-icon {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      }

      &.thread-icon {
        background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      }

      &.uptime-icon {
        background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
      }

      &.request-icon {
        background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
      }

      &.response-icon {
        background: linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%);
      }

      &.rps-icon {
        background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
      }
    }

    .metric-content {
      flex: 1;

      .metric-value {
        font-size: 24px;
        font-weight: 700;
        color: #333;
        margin-bottom: 4px;
      }

      .metric-label {
        font-size: 13px;
        color: #666;
        margin-bottom: 2px;
      }

      .metric-sub {
        font-size: 12px;
        color: #999;
      }
    }
  }
}
</style>
