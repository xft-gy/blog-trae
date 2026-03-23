import request from '@/utils/request'

export interface ArticleDTO {
  id: number
  title: string
  summary?: string
  content: string
  contentHtml?: string
  coverImage?: string
  authorId: number
  authorName?: string
  categoryId?: number
  categoryName?: string
  viewCount: number
  likeCount: number
  commentCount: number
  status: number
  isTop: number
  createdTime: string
  updatedTime: string
  publishedTime?: string
  tagIds?: number[]
  tagNames?: string[]
}

export interface CreateArticleRequest {
  title: string
  summary?: string
  content: string
  coverImage?: string
  categoryId?: number
  tagIds?: number[]
  status?: number
}

export interface UpdateArticleRequest {
  title: string
  summary?: string
  content: string
  coverImage?: string
  categoryId?: number
  tagIds?: number[]
  status?: number
}

export interface CommentDTO {
  id: number
  articleId: number
  userId: number
  userName?: string
  userAvatar?: string
  parentId?: number
  content: string
  likeCount: number
  status: number
  createdTime: string
  updatedTime: string
}

export interface CreateCommentRequest {
  articleId: number
  parentId?: number
  content: string
}

export const getArticleById = (id: number): Promise<ArticleDTO> => {
  return request.get(`/articles/${id}`)
}

export const getPublishedArticles = (page = 1, size = 10): Promise<ArticleDTO[]> => {
  return request.get('/articles', { params: { page, size } })
}

export const getArticlesByCategory = (categoryId: number, page = 1, size = 10): Promise<ArticleDTO[]> => {
  return request.get(`/articles/category/${categoryId}`, { params: { page, size } })
}

export interface SearchResultDTO {
  article: ArticleDTO
  highlightedTitle?: string
  highlightedSummary?: string
  highlightedContent?: string
}

export const searchArticles = (keyword: string, page = 1, size = 10): Promise<ArticleDTO[]> => {
  return request.get('/articles/search', { params: { keyword, page, size } })
}

export const advancedSearchArticles = (params: {
  keyword?: string
  categoryId?: number
  sortBy?: string
  sortOrder?: string
  page?: number
  size?: number
}): Promise<SearchResultDTO[]> => {
  return request.get('/articles/search/advanced', { params })
}

export const createArticle = (data: CreateArticleRequest): Promise<ArticleDTO> => {
  return request.post('/articles', data)
}

export const updateArticle = (id: number, data: UpdateArticleRequest): Promise<ArticleDTO> => {
  return request.put(`/articles/${id}`, data)
}

export const deleteArticle = (id: number): Promise<void> => {
  return request.delete(`/articles/${id}`)
}

export const getMyDraftArticles = (page = 1, size = 10): Promise<ArticleDTO[]> => {
  return request.get('/articles/my/drafts', { params: { page, size } })
}

export const getMyArticles = (page = 1, size = 10): Promise<ArticleDTO[]> => {
  return request.get('/articles/my', { params: { page, size } })
}

export const saveDraft = (data: CreateArticleRequest): Promise<ArticleDTO> => {
  return request.post('/articles/draft', data)
}

export const getAllArticlesAdmin = (page = 1, size = 10): Promise<ArticleDTO[]> => {
  return request.get('/articles/admin/all', { params: { page, size } })
}

export const publishArticleAdmin = (id: number): Promise<void> => {
  return request.put(`/articles/admin/${id}/publish`)
}

export const offlineArticleAdmin = (id: number): Promise<void> => {
  return request.put(`/articles/admin/${id}/offline`)
}

export const getCommentsByArticleId = (articleId: number, page = 1, size = 10): Promise<CommentDTO[]> => {
  return request.get(`/comments/article/${articleId}`, { params: { page, size } })
}

export const getRepliesByParentId = (parentId: number, page = 1, size = 10): Promise<CommentDTO[]> => {
  return request.get(`/comments/parent/${parentId}`, { params: { page, size } })
}

export const createComment = (data: CreateCommentRequest): Promise<CommentDTO> => {
  return request.post('/comments', data)
}

export const deleteComment = (id: number): Promise<void> => {
  return request.delete(`/comments/${id}`)
}

export const likeComment = (id: number): Promise<void> => {
  return request.post(`/comments/${id}/like`)
}

export const unlikeComment = (id: number): Promise<void> => {
  return request.post(`/comments/${id}/unlike`)
}

export const toggleLike = (targetType: string, targetId: number): Promise<void> => {
  return request.post(`/interaction/like/${targetType}/${targetId}`)
}

export const checkIfLiked = (targetType: string, targetId: number): Promise<boolean> => {
  return request.get(`/interaction/like/${targetType}/${targetId}`)
}

export const toggleFavorite = (articleId: number): Promise<void> => {
  return request.post(`/interaction/favorite/${articleId}`)
}

export const checkIfFavorited = (articleId: number): Promise<boolean> => {
  return request.get(`/interaction/favorite/${articleId}`)
}
