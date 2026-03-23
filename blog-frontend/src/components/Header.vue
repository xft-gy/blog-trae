<template>
  <header class="header">
    <div class="container">
      <div class="header-content">
        <router-link to="/" class="logo">
          <span class="logo-text">专业知识分享博客</span>
        </router-link>
        
        <nav class="nav">
          <router-link to="/" class="nav-item">首页</router-link>
          <router-link to="/" class="nav-item">分类</router-link>
          <router-link to="/" class="nav-item">标签</router-link>
        </nav>
        
        <div class="search-box">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索文章..."
            :prefix-icon="Search"
            @keyup.enter="handleSearch"
            clearable
          />
        </div>
        
        <div class="user-actions">
          <template v-if="userStore.isLoggedIn">
            <el-dropdown @command="handleCommand">
              <span class="user-info">
                <el-avatar :size="32" :src="userStore.userInfo?.avatar">
                  {{ userStore.userInfo?.nickname?.charAt(0) }}
                </el-avatar>
                <span class="username">{{ userStore.userInfo?.nickname }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="admin">后台管理</el-dropdown-item>
                  <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                  <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login">
              <el-button type="primary">登录</el-button>
            </router-link>
            <router-link to="/register">
              <el-button>注册</el-button>
            </router-link>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const searchKeyword = ref('')

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({ name: 'Search', query: { keyword: searchKeyword.value } })
  }
}

const handleCommand = (command: string) => {
  if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/')
  } else if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'admin') {
    router.push('/admin')
  }
}
</script>

<style scoped lang="scss">
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  gap: 24px;
}

.logo {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  
  .logo-text {
    font-size: 22px;
    font-weight: bold;
    color: #1890ff;
  }
}

.nav {
  display: flex;
  gap: 32px;
  flex-shrink: 0;
  
  .nav-item {
    font-size: 16px;
    color: #333;
    transition: color 0.3s;
    
    &:hover,
    &.router-link-active {
      color: #1890ff;
    }
  }
}

.search-box {
  flex: 1;
  max-width: 400px;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 12px;
  
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

@media (max-width: 768px) {
  .nav {
    display: none;
  }
  
  .logo-text {
    font-size: 18px;
  }
}
</style>
