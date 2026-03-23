<template>
  <div class="category-page">
    <Header />
    <div class="container">
      <div class="content-wrapper">
        <main class="main-content">
          <el-empty description="分类页面开发中" />
        </main>
        
        <aside class="sidebar">
          <div class="card">
            <h3>所有分类</h3>
            <el-empty description="暂无分类" />
          </div>
        </aside>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Header from '@/components/Header.vue'
</script>

<style scoped lang="scss">
.category-page {
  min-height: 100vh;
}

.content-wrapper {
  display: flex;
  gap: 20px;
  margin-top: 20px;
  padding-bottom: 40px;
}

.main-content {
  flex: 1;
}

.sidebar {
  width: 300px;
  flex-shrink: 0;
}

@media (max-width: 768px) {
  .content-wrapper {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
  }
}
</style>
