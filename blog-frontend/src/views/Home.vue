<template>
  <div class="home">
    <Header />
    <div class="container">
      <div class="content-wrapper">
        <main class="main-content">
          <div class="welcome-section">
            <h1>专业知识分享博客</h1>
            <p>探索软件开发、AI学习及大模型应用开发的专业知识</p>
          </div>
          
          <div class="section">
            <h2 class="section-title">最新文章</h2>
            <div v-if="loading" class="loading-wrapper">
              <el-skeleton :rows="5" animated />
            </div>
            <div v-else-if="articles.length > 0" class="article-list">
              <div v-for="article in articles" :key="article.id" class="article-card" @click="goToArticle(article.id)">
                <div v-if="article.coverImage" class="article-cover">
                  <img :src="article.coverImage" :alt="article.title" />
                </div>
                <div class="article-content">
                  <h3 class="article-title">{{ article.title }}</h3>
                  <p v-if="article.summary" class="article-summary">{{ article.summary }}</p>
                  <div class="article-meta">
                    <span class="meta-item">
                      <el-icon><View /></el-icon>
                      {{ article.viewCount }}
                    </span>
                    <span class="meta-item">
                      <el-icon><ChatDotRound /></el-icon>
                      {{ article.commentCount }}
                    </span>
                    <span class="meta-item">
                      <el-icon><Star /></el-icon>
                      {{ article.likeCount }}
                    </span>
                    <span class="meta-item publish-time">
                      {{ formatDate(article.publishedTime || article.createdTime) }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
            <el-empty v-else description="暂无文章" />
          </div>
        </main>
        
        <aside class="sidebar">
          <div class="card">
            <h3>分类</h3>
            <el-empty description="暂无分类" />
          </div>
          <div class="card">
            <h3>热门标签</h3>
            <el-empty description="暂无标签" />
          </div>
        </aside>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { View, ChatDotRound, Star } from '@element-plus/icons-vue'
import Header from '@/components/Header.vue'
import { getPublishedArticles, type ArticleDTO } from '@/api/article'

const router = useRouter()
const loading = ref(false)
const articles = ref<ArticleDTO[]>([])

const loadArticles = async () => {
  loading.value = true
  try {
    articles.value = await getPublishedArticles(1, 10)
  } finally {
    loading.value = false
  }
}

const goToArticle = (id: number) => {
  router.push(`/article/${id}`)
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN')
}

onMounted(() => {
  loadArticles()
})
</script>

<style scoped lang="scss">
.home {
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

.welcome-section {
  text-align: center;
  padding: 60px 0;
  
  h1 {
    font-size: 36px;
    margin-bottom: 16px;
    color: #1890ff;
  }
  
  p {
    font-size: 18px;
    color: #666;
  }
}

.section {
  .section-title {
    font-size: 24px;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #1890ff;
  }
}

.loading-wrapper {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  display: flex;
  gap: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
  }
}

.article-cover {
  width: 200px;
  height: 140px;
  flex-shrink: 0;
  border-radius: 8px;
  overflow: hidden;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.article-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.article-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 12px;
  line-height: 1.4;
}

.article-summary {
  font-size: 14px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 12px;
  flex: 1;
}

.article-meta {
  display: flex;
  align-items: center;
  gap: 16px;
  font-size: 14px;
  color: #999;
  
  .meta-item {
    display: flex;
    align-items: center;
    gap: 4px;
  }
  
  .publish-time {
    margin-left: auto;
  }
}

.sidebar {
  width: 300px;
  flex-shrink: 0;
  
  .card {
    h3 {
      margin-bottom: 16px;
      font-size: 18px;
    }
  }
}

@media (max-width: 768px) {
  .content-wrapper {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
  }
  
  .welcome-section {
    padding: 40px 0;
    
    h1 {
      font-size: 28px;
    }
    
    p {
      font-size: 16px;
    }
  }
  
  .article-card {
    flex-direction: column;
  }
  
  .article-cover {
    width: 100%;
    height: 200px;
  }
}
</style>
