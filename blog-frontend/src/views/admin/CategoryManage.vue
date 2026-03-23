<template>
  <div class="category-manage">
    <div class="page-header">
      <h2>分类管理</h2>
      <el-button type="primary" @click="showCreateDialog">
        <el-icon><Plus /></el-icon>
        新建分类
      </el-button>
    </div>
    
    <el-card class="table-card">
      <el-table :data="categories" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="slug" label="标识" width="150" />
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column prop="sortOrder" label="排序" width="100" />
        <el-table-column prop="articleCount" label="文章数" width="100" />
        <el-table-column prop="createdTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="editCategory(row)">编辑</el-button>
            <el-button link type="danger" size="small" @click="deleteCategory(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑分类' : '新建分类'"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form :model="formData" label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="formData.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="标识">
          <el-input v-model="formData.slug" placeholder="请输入分类标识" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="3"
            placeholder="请输入分类描述"
          />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="formData.sortOrder" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCategory" :loading="submitting">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getAllCategories, createCategory, updateCategory, deleteCategory, type CategoryDTO, type CreateCategoryRequest } from '@/api/category'

const categories = ref<CategoryDTO[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formData = ref<Partial<CreateCategoryRequest & { id?: number }>>({
  name: '',
  slug: '',
  description: '',
  sortOrder: 0
})

const loadCategories = async () => {
  loading.value = true
  try {
    categories.value = await getAllCategories()
  } catch (error) {
    ElMessage.error('加载分类列表失败')
  } finally {
    loading.value = false
  }
}

const showCreateDialog = () => {
  isEdit.value = false
  formData.value = {
    name: '',
    slug: '',
    description: '',
    sortOrder: 0
  }
  dialogVisible.value = true
}

const editCategory = (category: CategoryDTO) => {
  isEdit.value = true
  formData.value = {
    id: category.id,
    name: category.name,
    slug: category.slug,
    description: category.description,
    sortOrder: category.sortOrder
  }
  dialogVisible.value = true
}

const deleteCategoryHandler = async (category: CategoryDTO) => {
  try {
    await ElMessageBox.confirm('确定要删除这个分类吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteCategory(category.id)
    ElMessage.success('删除成功')
    loadCategories()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const submitCategory = async () => {
  if (!formData.value.name || !formData.value.slug) {
    ElMessage.warning('请填写完整的分类信息')
    return
  }
  
  submitting.value = true
  try {
    if (isEdit.value && formData.value.id) {
      await updateCategory(formData.value.id, formData.value as CreateCategoryRequest)
      ElMessage.success('更新成功')
    } else {
      await createCategory(formData.value as CreateCategoryRequest)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadCategories()
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
  loadCategories()
})
</script>

<style scoped lang="scss">
.category-manage {
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
