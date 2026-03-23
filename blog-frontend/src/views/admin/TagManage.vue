<template>
  <div class="tag-manage">
    <div class="page-header">
      <h2>标签管理</h2>
      <el-button type="primary" @click="showCreateDialog">
        <el-icon><Plus /></el-icon>
        新建标签
      </el-button>
    </div>
    
    <el-card class="table-card">
      <el-table :data="tags" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="slug" label="标识" width="150" />
        <el-table-column label="颜色" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.color" :color="row.color">{{ row.color }}</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="articleCount" label="文章数" width="100" />
        <el-table-column prop="createdTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="editTag(row)">编辑</el-button>
            <el-button link type="danger" size="small" @click="deleteTag(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑标签' : '新建标签'"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form :model="formData" label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="formData.name" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="标识">
          <el-input v-model="formData.slug" placeholder="请输入标签标识" />
        </el-form-item>
        <el-form-item label="颜色">
          <el-color-picker v-model="formData.color" show-alpha />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitTag" :loading="submitting">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getAllTags, createTag, updateTag, deleteTag, type TagDTO, type CreateTagRequest } from '@/api/category'

const tags = ref<TagDTO[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formData = ref<Partial<CreateTagRequest & { id?: number }>>({
  name: '',
  slug: '',
  color: ''
})

const loadTags = async () => {
  loading.value = true
  try {
    tags.value = await getAllTags()
  } catch (error) {
    ElMessage.error('加载标签列表失败')
  } finally {
    loading.value = false
  }
}

const showCreateDialog = () => {
  isEdit.value = false
  formData.value = {
    name: '',
    slug: '',
    color: ''
  }
  dialogVisible.value = true
}

const editTag = (tag: TagDTO) => {
  isEdit.value = true
  formData.value = {
    id: tag.id,
    name: tag.name,
    slug: tag.slug,
    color: tag.color
  }
  dialogVisible.value = true
}

const deleteTagHandler = async (tag: TagDTO) => {
  try {
    await ElMessageBox.confirm('确定要删除这个标签吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteTag(tag.id)
    ElMessage.success('删除成功')
    loadTags()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const submitTag = async () => {
  if (!formData.value.name || !formData.value.slug) {
    ElMessage.warning('请填写完整的标签信息')
    return
  }
  
  submitting.value = true
  try {
    if (isEdit.value && formData.value.id) {
      await updateTag(formData.value.id, formData.value as CreateTagRequest)
      ElMessage.success('更新成功')
    } else {
      await createTag(formData.value as CreateTagRequest)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadTags()
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
  loadTags()
})
</script>

<style scoped lang="scss">
.tag-manage {
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
