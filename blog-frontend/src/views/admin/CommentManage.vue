<template>
  <div class="comment-manage">
    <div class="page-header">
      <h2>评论管理</h2>
    </div>
    
    <el-card class="table-card">
      <el-table :data="comments" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="userName" label="用户" width="120" />
        <el-table-column prop="content" label="内容" min-width="300" show-overflow-tooltip />
        <el-table-column prop="likeCount" label="点赞数" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">
              {{ row.status === 1 ? '已通过' : '待审核' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="danger" size="small" @click="deleteComment(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-empty v-if="!loading && comments.length === 0" description="暂无评论" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { type CommentDTO, deleteComment as deleteCommentApi } from '@/api/article'

const comments = ref<CommentDTO[]>([])
const loading = ref(false)

const loadComments = async () => {
  loading.value = true
  try {
    comments.value = []
    ElMessage.info('评论列表功能需要后端支持获取所有评论')
  } catch (error) {
    ElMessage.error('加载评论列表失败')
  } finally {
    loading.value = false
  }
}

const deleteComment = async (comment: CommentDTO) => {
  try {
    await ElMessageBox.confirm('确定要删除这条评论吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteCommentApi(comment.id)
    ElMessage.success('删除成功')
    loadComments()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  loadComments()
})
</script>

<style scoped lang="scss">
.comment-manage {
  .page-header {
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
