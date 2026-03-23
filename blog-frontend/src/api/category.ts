import request from '@/utils/request'

export interface CategoryDTO {
  id: number
  name: string
  slug: string
  description?: string
  icon?: string
  sortOrder: number
  articleCount: number
  createdTime: string
  updatedTime: string
}

export interface TagDTO {
  id: number
  name: string
  slug: string
  color?: string
  articleCount: number
  createdTime: string
  updatedTime: string
}

export interface CreateCategoryRequest {
  name: string
  slug: string
  description?: string
  icon?: string
  sortOrder?: number
}

export interface CreateTagRequest {
  name: string
  slug: string
  color?: string
}

export const getAllCategories = (): Promise<CategoryDTO[]> => {
  return request.get('/categories')
}

export const getCategories = getAllCategories

export const getCategoryById = (id: number): Promise<CategoryDTO> => {
  return request.get(`/categories/${id}`)
}

export const getCategoryBySlug = (slug: string): Promise<CategoryDTO> => {
  return request.get(`/categories/slug/${slug}`)
}

export const createCategory = (data: CreateCategoryRequest): Promise<CategoryDTO> => {
  return request.post('/categories', data)
}

export const updateCategory = (id: number, data: CreateCategoryRequest): Promise<CategoryDTO> => {
  return request.put(`/categories/${id}`, data)
}

export const deleteCategory = (id: number): Promise<void> => {
  return request.delete(`/categories/${id}`)
}

export const getAllTags = (): Promise<TagDTO[]> => {
  return request.get('/tags')
}

export const getTagById = (id: number): Promise<TagDTO> => {
  return request.get(`/tags/${id}`)
}

export const getTagBySlug = (slug: string): Promise<TagDTO> => {
  return request.get(`/tags/slug/${slug}`)
}

export const createTag = (data: CreateTagRequest): Promise<TagDTO> => {
  return request.post('/tags', data)
}

export const updateTag = (id: number, data: CreateTagRequest): Promise<TagDTO> => {
  return request.put(`/tags/${id}`, data)
}

export const deleteTag = (id: number): Promise<void> => {
  return request.delete(`/tags/${id}`)
}
