<template>
  <div class="profile-page">
    <Header />
    <div class="container">
      <div class="profile-wrapper">
        <el-card class="profile-card">
          <template #header>
            <div class="card-header">
              <span>个人中心</span>
            </div>
          </template>
          
          <div class="profile-info">
            <div class="avatar-section">
              <el-avatar :size="100">
                {{ userStore.userInfo?.nickname?.charAt(0) || 'U' }}
              </el-avatar>
            </div>
            
            <el-form :model="formData" label-width="100px" class="profile-form">
              <el-form-item label="用户名">
                <el-input v-model="formData.username" disabled />
              </el-form-item>
              <el-form-item label="昵称">
                <el-input v-model="formData.nickname" placeholder="请输入昵称" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="formData.email" placeholder="请输入邮箱" disabled />
              </el-form-item>
              <el-form-item label="个人简介">
                <el-input
                  v-model="formData.bio"
                  type="textarea"
                  :rows="4"
                  placeholder="介绍一下自己吧..."
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="saveProfile" :loading="saving">保存修改</el-button>
                <el-button @click="resetForm">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
        
        <el-card class="articles-card" style="margin-top: 24px;">
          <template #header>
            <div class="card-header">
              <span>我的文章</span>
            </div>
          </template>
          
          <el-tabs v-model="activeTab" @tab-change="handleTabChange">
            <el-tab-pane label="全部文章" name="all">
              <div class="article-list" v-loading="loading">
                <el-empty v-if="myArticles.length === 0 && !loading" description="暂无文章" />
                <div v-for="article in myArticles" :key="article.id" class="article-item">
                  <div class="article-title">
                    <router-link :to="`/article/${article.id}`">{{ article.title }}</router-link>
                    <el-tag :type="article.status === 1 ? 'success' : 'info'" size="small">
                      {{ article.status === 1 ? '已发布' : '草稿' }}
                    </el-tag>
                  </div>
                  <div class="article-meta">
                    <span>{{ article.updatedTime }}</span>
                    <span>阅读: {{ article.viewCount }}</span>
                  </div>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="草稿" name="drafts">
              <div class="article-list" v-loading="loading">
                <el-empty v-if="draftArticles.length === 0 && !loading" description="暂无草稿" />
                <div v-for="article in draftArticles" :key="article.id" class="article-item">
                  <div class="article-title">
                    <router-link :to="`/article/${article.id}`">{{ article.title }}</router-link>
                  </div>
                  <div class="article-meta">
                    <span>最后修改: {{ article.updatedTime }}</span>
                  </div>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import Header from '@/components/Header.vue'
import { useUserStore } from '@/stores/user'
import { getCurrentUserProfile, updateCurrentUserProfile, type UpdateUserRequest } from '@/api/user'
import { getMyArticles, getMyDraftArticles, type ArticleDTO } from '@/api/article'

const userStore = useUserStore()
const saving = ref(false)
const loading = ref(false)
const activeTab = ref('all')
const formData = ref({
  username: '',
  nickname: '',
  email: '',
  phone: '',
  bio: '',
  avatar: ''
})
const myArticles = ref<ArticleDTO[]>([])
const draftArticles = ref<ArticleDTO[]>([])

const loadProfile = async () => {
  loading.value = true
  try {
    const res = await getCurrentUserProfile()
    const user = res.data
    formData.value = {
      username: user.username || '',
      nickname: user.nickname || '',
      email: user.email || '',
      phone: user.phone || '',
      bio: user.bio || '',
      avatar: user.avatar || ''
    }
  } catch (error) {
    ElMessage.error('加载用户信息失败')
  } finally {
    loading.value = false
  }
}

const loadMyArticles = async () => {
  loading.value = true
  try {
    const res = await getMyArticles()
    myArticles.value = res.data
  } catch (error) {
    ElMessage.error('加载文章失败')
  } finally {
    loading.value = false
  }
}

const loadDraftArticles = async () => {
  loading.value = true
  try {
    const res = await getMyDraftArticles()
    draftArticles.value = res.data
  } catch (error) {
    ElMessage.error('加载草稿失败')
  } finally {
    loading.value = false
  }
}

const handleTabChange = (tabName: string) => {
  if (tabName === 'all') {
    loadMyArticles()
  } else if (tabName === 'drafts') {
    loadDraftArticles()
  }
}

const saveProfile = async () => {
  saving.value = true
  try {
    const updateData: UpdateUserRequest = {
      nickname: formData.value.nickname,
      bio: formData.value.bio,
      avatar: formData.value.avatar
    }
    await updateCurrentUserProfile(updateData)
    ElMessage.success('保存成功')
    await loadProfile()
    await userStore.fetchUserInfo()
  } catch (error) {
    ElMessage.error('保存失败')
  } finally {
    saving.value = false
  }
}

const resetForm = () => {
  loadProfile()
}

onMounted(() => {
  loadProfile()
  loadMyArticles()
})
</script>

<style scoped lang="scss">
.profile-page {
  min-height: 100vh;
  background: #f5f7fa;
  padding-bottom: 40px;
}

.profile-wrapper {
  padding: 40px 0;
  max-width: 900px;
  margin: 0 auto;
}

.card-header {
  font-size: 18px;
  font-weight: 600;
}

.profile-info {
  display: flex;
  gap: 40px;
  
  .avatar-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex-shrink: 0;
  }
  
  .profile-form {
    flex: 1;
  }
}

.articles-card {
  .article-list {
    .article-item {
      padding: 16px 0;
      border-bottom: 1px solid #f0f0f0;
      
      &:last-child {
        border-bottom: none;
      }
      
      .article-title {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 8px;
        
        a {
          font-size: 16px;
          color: #1890ff;
          text-decoration: none;
          
          &:hover {
            text-decoration: underline;
          }
        }
      }
      
      .article-meta {
        display: flex;
        gap: 16px;
        font-size: 13px;
        color: #999;
      }
    }
  }
}

@media (max-width: 768px) {
  .profile-info {
    flex-direction: column;
    align-items: center;
  }
  
  .profile-form {
    width: 100%;
  }
}
</style>
