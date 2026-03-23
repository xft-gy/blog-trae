<template>
  <div class="search-page">
    <Header />
    <div class="container">
      <div class="search-results">
        <h2 class="page-title" v-if="keyword">
          搜索结果: <span class="keyword">"{{ keyword }}"</span>
        </h2>
        
        <div class="search-filters" v-if="keyword">
          <div class="filter-group">
            <label>分类:</label>
            <el-select v-model="filters.categoryId" placeholder="全部" clearable @change="loadSearchResults">
              <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id" />
            </el-select>
          </div>
          <div class="filter-group">
            <label>排序:</label>
            <el-select v-model="filters.sortBy" placeholder="时间" @change="loadSearchResults">
              <el-option label="时间" value="time" />
              <el-option label="浏览量" value="viewCount" />
              <el-option label="点赞数" value="likeCount" />
              <el-option label="评论数" value="commentCount" />
            </el-select>
          </div>
          <div class="filter-group">
            <label>顺序:</label>
            <el-select v-model="filters.sortOrder" placeholder="降序" @change="loadSearchResults">
              <el-option label="降序" value="desc" />
              <el-option label="升序" value="asc" />
            </el-select>
          </div>
        </div>
        
        <div class="results-list" v-if="!loading && searchResults.length > 0">
          <article class="article-card" v-for="result in searchResults" :key="result.article.id" @click="goToArticle(result.article.id)">
            <div class="article-info">
              <h3 class="article-title" v-html="result.highlightedTitle || result.article.title"></h3>
              <p class="article-summary" v-html="result.highlightedSummary || result.article.summary"></p>
              <div class="article-meta">
                <span class="meta-item">
                  <el-icon><User /></el-icon>
                  {{ result.article.authorName || '作者' }}
                </span>
                <span class="meta-item">
                  <el-icon><View /></el-icon>
                  {{ result.article.viewCount }}
                </span>
                <span class="meta-item">
                  <el-icon><ChatDotRound /></el-icon>
                  {{ result.article.commentCount }}
                </span>
                <span class="meta-item">
                  <el-icon><Star /></el-icon>
                  {{ result.article.likeCount }}
                </span>
                <span class="meta-item publish-time">
                  {{ formatDate(result.article.publishedTime || result.article.createdTime) }}
                </span>
              </div>
              <div class="article-tags" v-if="result.article.tagNames && result.article.tagNames.length">
                <el-tag v-for="tag in result.article.tagNames" :key="tag" size="small" class="tag-item">{{ tag }}</el-tag>
              </div>
            </div>
          </article>
        </div>
        
        <el-empty v-if="!loading && searchResults.length === 0" description="没有找到相关文章" />
        
        <div v-if="loading" class="loading">
          <el-icon class="is-loading" :size="40"><Loading /></el-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { User, View, ChatDotRound, Star, Loading } from '@element-plus/icons-vue'
import Header from '@/components/Header.vue'
import { advancedSearchArticles, type SearchResultDTO } from '@/api/article'
import { getCategories, type CategoryDTO } from '@/api/category'

const route = useRoute()
const router = useRouter()
const keyword = ref('')
const searchResults = ref<SearchResultDTO[]>([])
const categories = ref<CategoryDTO[]>([])
const loading = ref(false)

const filters = ref({
  categoryId: undefined as number | undefined,
  sortBy: 'time',
  sortOrder: 'desc'
})

const loadCategories = async () => {
  try {
    categories.value = await getCategories()
  } catch (error) {
    console.error('Load categories failed:', error)
  }
}

const loadSearchResults = async () => {
  const searchKeyword = route.query.keyword as string
  if (!searchKeyword) {
    searchResults.value = []
    return
  }
  
  keyword.value = searchKeyword
  loading.value = true
  try {
    searchResults.value = await advancedSearchArticles({
      keyword: searchKeyword,
      categoryId: filters.value.categoryId,
      sortBy: filters.value.sortBy,
      sortOrder: filters.value.sortOrder
    })
  } catch (error) {
    console.error('Search failed:', error)
  } finally {
    loading.value = false
  }
}

const goToArticle = (id: number) => {
  router.push({ name: 'ArticleDetail', params: { id } })
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  loadCategories()
  loadSearchResults()
})

watch(() => route.query.keyword, () => {
  filters.value.categoryId = undefined
  filters.value.sortBy = 'time'
  filters.value.sortOrder = 'desc'
  loadSearchResults()
})
</script>

<style scoped lang="scss">
.search-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.search-results {
  padding: 40px 0;
  
  .page-title {
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 24px;
    color: #333;
    
    .keyword {
      color: #1890ff;
    }
  }
  
  .search-filters {
    display: flex;
    gap: 20px;
    margin-bottom: 24px;
    padding: 16px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    
    .filter-group {
      display: flex;
      align-items: center;
      gap: 8px;
      
      label {
        font-size: 14px;
        color: #666;
        white-space: nowrap;
      }
    }
  }
  
  .results-list {
    .article-card {
      background: #fff;
      border-radius: 8px;
      padding: 24px;
      margin-bottom: 16px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
      cursor: pointer;
      transition: all 0.3s;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
      }
      
      .article-info {
        .article-title {
          font-size: 20px;
          font-weight: 600;
          color: #333;
          margin-bottom: 12px;
          line-height: 1.4;
          
          em {
            color: #f56c6c;
            font-style: normal;
            background: #fef0f0;
            padding: 0 2px;
          }
        }
        
        .article-summary {
          font-size: 14px;
          color: #666;
          line-height: 1.6;
          margin-bottom: 16px;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          overflow: hidden;
          
          em {
            color: #f56c6c;
            font-style: normal;
            background: #fef0f0;
            padding: 0 2px;
          }
        }
        
        .article-meta {
          display: flex;
          align-items: center;
          gap: 16px;
          font-size: 13px;
          color: #999;
          flex-wrap: wrap;
          margin-bottom: 12px;
          
          .meta-item {
            display: flex;
            align-items: center;
            gap: 4px;
          }
          
          .publish-time {
            margin-left: auto;
          }
        }
        
        .article-tags {
          display: flex;
          gap: 8px;
          flex-wrap: wrap;
          
          .tag-item {
            cursor: pointer;
          }
        }
      }
    }
  }
  
  .loading {
    display: flex;
    justify-content: center;
    padding: 60px 0;
  }
}
</style>
