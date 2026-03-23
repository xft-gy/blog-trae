<template>
  <el-container class="admin-layout">
    <el-aside width="220px" class="admin-sidebar">
      <div class="sidebar-header">
        <h2>后台管理</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        class="sidebar-menu"
      >
        <el-menu-item index="/admin/articles">
          <el-icon><Document /></el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/categories">
          <el-icon><Folder /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/tags">
          <el-icon><PriceTag /></el-icon>
          <span>标签管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/comments">
          <el-icon><ChatDotRound /></el-icon>
          <span>评论管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/users">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/statistics">
          <el-icon><DataLine /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/admin/performance">
          <el-icon><Monitor /></el-icon>
          <span>性能监控</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container class="admin-main">
      <el-header class="admin-header">
        <div class="header-left">
          <router-link to="/" class="back-link">
            <el-icon><ArrowLeft /></el-icon>
            返回首页
          </router-link>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32">
                {{ userStore.userInfo?.nickname?.charAt(0) || 'A' }}
              </el-avatar>
              <span class="username">{{ userStore.userInfo?.nickname || '管理员' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main class="admin-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Document, Folder, PriceTag, ChatDotRound, ArrowLeft, User, DataLine, Monitor } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleCommand = (command: string) => {
  if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/')
  }
}
</script>

<style scoped lang="scss">
.admin-layout {
  height: 100vh;
}

.admin-sidebar {
  background: #001529;
  color: #fff;
  
  .sidebar-header {
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    
    h2 {
      font-size: 18px;
      font-weight: 600;
      color: #fff;
      margin: 0;
    }
  }
  
  .sidebar-menu {
    border-right: none;
    background: transparent;
    
    :deep(.el-menu-item) {
      color: rgba(255, 255, 255, 0.8);
      
      &:hover,
      &.is-active {
        background: #1890ff;
        color: #fff;
      }
    }
  }
}

.admin-main {
  background: #f0f2f5;
}

.admin-header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  
  .header-left {
    .back-link {
      display: flex;
      align-items: center;
      gap: 4px;
      color: #666;
      text-decoration: none;
      font-size: 14px;
      
      &:hover {
        color: #1890ff;
      }
    }
  }
  
  .header-right {
    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      
      .username {
        font-size: 14px;
        color: #333;
      }
    }
  }
}

.admin-content {
  padding: 24px;
}
</style>
