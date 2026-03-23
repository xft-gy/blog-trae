<template>
  <div class="statistics-dashboard">
    <el-card class="stats-card">
      <template #header>
        <div class="card-header">
          <span>数据统计</span>
          <el-button type="primary" @click="loadStatistics" :loading="loading">
            刷新
          </el-button>
        </div>
      </template>

      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-icon user-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics?.totalUsers || 0 }}</div>
              <div class="stat-label">总用户数</div>
              <div class="stat-today">今日新增: {{ statistics?.todayNewUsers || 0 }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-icon article-icon">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics?.totalArticles || 0 }}</div>
              <div class="stat-label">总文章数</div>
              <div class="stat-today">今日新增: {{ statistics?.todayNewArticles || 0 }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-icon comment-icon">
              <el-icon><ChatDotRound /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics?.totalComments || 0 }}</div>
              <div class="stat-label">总评论数</div>
              <div class="stat-today">今日新增: {{ statistics?.todayNewComments || 0 }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-icon view-icon">
              <el-icon><View /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics?.totalViews || 0 }}</div>
              <div class="stat-label">总浏览量</div>
              <div class="stat-today">今日浏览: {{ statistics?.todayTotalViews || 0 }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Document, ChatDotRound, View } from '@element-plus/icons-vue'
import { getStatistics, type StatisticsDTO } from '@/api/statistics'

const loading = ref(false)
const statistics = ref<StatisticsDTO | null>(null)

const loadStatistics = async () => {
  loading.value = true
  try {
    const res = await getStatistics()
    statistics.value = res.data
  } catch (error) {
    ElMessage.error('加载统计数据失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadStatistics()
})
</script>

<style scoped lang="scss">
.statistics-dashboard {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 18px;
    font-weight: 600;
  }

  .stat-item {
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 24px;
    background: #f8f9fa;
    border-radius: 8px;

    .stat-icon {
      width: 64px;
      height: 64px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 32px;
      color: #fff;

      &.user-icon {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      }

      &.article-icon {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      }

      &.comment-icon {
        background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      }

      &.view-icon {
        background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
      }
    }

    .stat-content {
      flex: 1;

      .stat-value {
        font-size: 32px;
        font-weight: 700;
        color: #333;
        margin-bottom: 4px;
      }

      .stat-label {
        font-size: 14px;
        color: #666;
        margin-bottom: 4px;
      }

      .stat-today {
        font-size: 12px;
        color: #1890ff;
      }
    }
  }
}
</style>
