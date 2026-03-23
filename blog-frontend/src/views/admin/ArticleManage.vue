<template>
  <div class="article-manage">
    <div class="page-header">
      <h2>文章管理</h2>
      <el-button type="primary" @click="showCreateDialog">
        <el-icon><Plus /></el-icon>
        新建文章
      </el-button>
    </div>
    
    <el-card class="table-card">
      <el-table :data="articles" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="authorName" label="作者" width="120" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="likeCount" label="点赞数" width="100" />
        <el-table-column prop="commentCount" label="评论数" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="320" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="editArticle(row)">编辑</el-button>
            <el-button link type="primary" size="small" @click="viewArticle(row)">查看</el-button>
            <el-button 
              v-if="row.status !== 1" 
              link 
              type="success" 
              size="small" 
              @click="handlePublish(row)"
            >
              发布
            </el-button>
            <el-button 
              v-if="row.status === 1" 
              link 
              type="warning" 
              size="small" 
              @click="handleOffline(row)"
            >
              下线
            </el-button>
            <el-button link type="danger" size="small" @click="deleteArticleHandler(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑文章' : '新建文章'"
      width="800px"
      :close-on-click-modal="false"
    >
      <el-form :model="formData" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="formData.title" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="摘要">
          <el-input
            v-model="formData.summary"
            type="textarea"
            :rows="3"
            placeholder="请输入文章摘要"
          />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="formData.categoryId" placeholder="请选择分类">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="formData.content"
            type="textarea"
            :rows="15"
            placeholder="请输入文章内容（Markdown格式）"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="formData.status">
            <el-radio :value="0">草稿</el-radio>
            <el-radio :value="1">发布</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitArticle" :loading="submitting">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getAllArticlesAdmin, publishArticleAdmin, offlineArticleAdmin, createArticle, updateArticle, deleteArticle, type ArticleDTO, type CreateArticleRequest, type UpdateArticleRequest } from '@/api/article'
import { getAllCategories, type CategoryDTO } from '@/api/category'

const router = useRouter()
const articles = ref<ArticleDTO[]>([])
const categories = ref<CategoryDTO[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formData = ref<Partial<CreateArticleRequest & { id?: number }>>({
  title: '',
  summary: '',
  content: '',
  categoryId: undefined,
  status: 0
})

const loadArticles = async () => {
  loading.value = true
  try {
    const res = await getAllArticlesAdmin(1, 100)
    articles.value = res.data
  } catch (error) {
    ElMessage.error('加载文章列表失败')
  } finally {
    loading.value = false
  }
}

const getStatusTagType = (status: number) => {
  switch (status) {
    case 0: return 'info'
    case 1: return 'success'
    case 2: return 'warning'
    default: return 'info'
  }
}

const getStatusText = (status: number) => {
  switch (status) {
    case 0: return '草稿'
    case 1: return '已发布'
    case 2: return '已下线'
    default: return '未知'
  }
}

const handlePublish = async (article: ArticleDTO) => {
  try {
    await ElMessageBox.confirm('确定要发布这篇文章吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await publishArticleAdmin(article.id)
    ElMessage.success('发布成功')
    loadArticles()
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('发布失败')
    }
  }
}

const handleOffline = async (article: ArticleDTO) => {
  try {
    await ElMessageBox.confirm('确定要下线这篇文章吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await offlineArticleAdmin(article.id)
    ElMessage.success('下线成功')
    loadArticles()
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('下线失败')
    }
  }
}

const loadCategories = async () => {
  try {
    categories.value = await getAllCategories()
  } catch (error) {
    console.error('Failed to load categories:', error)
  }
}

const showCreateDialog = () => {
  isEdit.value = false
  formData.value = {
    title: '',
    summary: '',
    content: '',
    categoryId: undefined,
    status: 0
  }
  dialogVisible.value = true
}

const editArticle = (article: ArticleDTO) => {
  isEdit.value = true
  formData.value = {
    id: article.id,
    title: article.title,
    summary: article.summary,
    content: article.content,
    categoryId: article.categoryId,
    status: article.status
  }
  dialogVisible.value = true
}

const viewArticle = (article: ArticleDTO) => {
  router.push({ name: 'ArticleDetail', params: { id: article.id } })
}

const deleteArticleHandler = async (article: ArticleDTO) => {
  try {
    await ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteArticle(article.id)
    ElMessage.success('删除成功')
    loadArticles()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const submitArticle = async () => {
  if (!formData.value.title || !formData.value.content) {
    ElMessage.warning('请填写完整的文章信息')
    return
  }
  
  submitting.value = true
  try {
    if (isEdit.value && formData.value.id) {
      await updateArticle(formData.value.id, formData.value as UpdateArticleRequest)
      ElMessage.success('更新成功')
    } else {
      await createArticle(formData.value as CreateArticleRequest)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadArticles()
  } catch (error) {
    ElMessage.error('保存失败')
  } finally {
    submitting.value = false
  }
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  loadArticles()
  loadCategories()
})
</script>

<style scoped lang="scss">
.article-manage {
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    
    h2 {
      margin: 0;
      font-size: 24px;
      font-weight: 600;
    }
  }
  
  .table-card {
    :deep(.el-card__body) {
      padding: 0;
    }
  }
}
</style>
