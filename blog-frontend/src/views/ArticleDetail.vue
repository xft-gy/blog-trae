<template>
  <div class="article-detail">
    <Header />
    <div class="container">
      <div class="content-wrapper">
        <main class="main-content" v-if="article">
          <article class="article">
            <header class="article-header">
              <h1 class="article-title">{{ article.title }}</h1>
              <div class="article-meta">
                <span class="meta-item">
                  <el-icon><User /></el-icon>
                  {{ article.authorName || '作者' }}
                </span>
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
            </header>
            <div class="article-body">
              <div v-if="article.content" class="article-content" v-html="renderContent(article.content)"></div>
              <div v-else class="article-content">{{ article.content }}</div>
            </div>
            <footer class="article-footer">
              <div class="article-tags" v-if="article.tagNames && article.tagNames.length">
                <el-tag v-for="tag in article.tagNames" :key="tag" class="tag-item">{{ tag }}</el-tag>
              </div>
              <div class="article-actions">
                <el-button :type="isLiked ? 'primary' : 'default'" @click="toggleLike">
                  <el-icon><Star /></el-icon>
                  {{ isLiked ? '已点赞' : '点赞' }} ({{ article.likeCount }})
                </el-button>
                <el-button :type="isFavorited ? 'primary' : 'default'" @click="toggleFavorite">
                  <el-icon><Collection /></el-icon>
                  {{ isFavorited ? '已收藏' : '收藏' }}
                </el-button>
                <el-button @click="shareArticle">
                  <el-icon><Share /></el-icon>
                  分享
                </el-button>
              </div>
            </footer>
          </article>
          
          <div class="comment-section">
            <h3 class="section-title">评论 ({{ comments.length }})</h3>
            
            <div class="comment-form">
              <el-input
                v-model="newComment"
                type="textarea"
                :rows="4"
                placeholder="写下你的评论..."
                maxlength="1000"
                show-word-limit
              />
              <div class="form-actions">
                <el-button type="primary" @click="submitComment" :loading="submitting">
                  发表评论
                </el-button>
              </div>
            </div>
            
            <div class="comment-list">
              <div v-for="comment in comments" :key="comment.id" class="comment-item">
                <div class="comment-avatar">
                  <el-avatar :size="40">
                    {{ (comment.userName || '用户').charAt(0) }}
                  </el-avatar>
                </div>
                <div class="comment-content-wrapper">
                  <div class="comment-header">
                    <span class="comment-author">{{ comment.userName || '匿名用户' }}</span>
                    <span class="comment-time">{{ formatDate(comment.createdTime) }}</span>
                  </div>
                  <div class="comment-text">{{ comment.content }}</div>
                  <div class="comment-actions">
                    <el-button link type="primary" size="small" @click="replyToComment(comment)">
                      <el-icon><ChatDotRound /></el-icon>
                      回复
                    </el-button>
                    <el-button link type="primary" size="small" @click="likeComment(comment)">
                      <el-icon><Star /></el-icon>
                      点赞 ({{ comment.likeCount }})
                    </el-button>
                    <el-button
                      v-if="canDeleteComment(comment)"
                      link
                      type="danger"
                      size="small"
                      @click="deleteComment(comment)"
                    >
                      <el-icon><Delete /></el-icon>
                      删除
                    </el-button>
                  </div>
                  
                  <div v-if="replyingTo && replyingTo.id === comment.id" class="reply-form">
                    <el-input
                      v-model="replyContent"
                      type="textarea"
                      :rows="3"
                      placeholder="回复评论..."
                      maxlength="1000"
                      show-word-limit
                    />
                    <div class="reply-actions">
                      <el-button size="small" @click="cancelReply">取消</el-button>
                      <el-button type="primary" size="small" @click="submitReply" :loading="submittingReply">
                        回复
                      </el-button>
                    </div>
                  </div>
                  
                  <div v-if="comment.replies && comment.replies.length > 0" class="replies">
                    <div v-for="reply in comment.replies" :key="reply.id" class="reply-item">
                      <div class="comment-avatar">
                        <el-avatar :size="32">
                          {{ (reply.userName || '用户').charAt(0) }}
                        </el-avatar>
                      </div>
                      <div class="comment-content-wrapper">
                        <div class="comment-header">
                          <span class="comment-author">{{ reply.userName || '匿名用户' }}</span>
                          <span class="comment-time">{{ formatDate(reply.createdTime) }}</span>
                        </div>
                        <div class="comment-text">{{ reply.content }}</div>
                        <div class="comment-actions">
                          <el-button link type="primary" size="small" @click="replyToComment(reply, comment)">
                            <el-icon><ChatDotRound /></el-icon>
                            回复
                          </el-button>
                          <el-button link type="primary" size="small" @click="likeComment(reply)">
                            <el-icon><Star /></el-icon>
                            点赞 ({{ reply.likeCount }})
                          </el-button>
                          <el-button
                            v-if="canDeleteComment(reply)"
                            link
                            type="danger"
                            size="small"
                            @click="deleteComment(reply)"
                          >
                            <el-icon><Delete /></el-icon>
                            删除
                          </el-button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              <el-empty v-if="comments.length === 0 && !loadingComments" description="暂无评论，快来抢沙发吧！" />
              <div v-if="loadingComments" class="loading">
                <el-icon class="is-loading" :size="40"><Loading /></el-icon>
              </div>
            </div>
          </div>
        </main>
        
        <aside class="sidebar">
          <div class="card">
            <h3>作者信息</h3>
            <el-empty description="暂无信息" />
          </div>
          <div class="card">
            <h3>相关文章</h3>
            <el-empty description="暂无相关文章" />
          </div>
        </aside>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, View, ChatDotRound, Star, Collection, Share, Delete, Loading } from '@element-plus/icons-vue'
import { marked } from 'marked'
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'
import Header from '@/components/Header.vue'
import { getArticleById, type ArticleDTO, getCommentsByArticleId, getRepliesByParentId, createComment, deleteComment as deleteCommentApi, type CommentDTO, type CreateCommentRequest, toggleLike as toggleLikeApi, checkIfLiked, toggleFavorite as toggleFavoriteApi, checkIfFavorited } from '@/api/article'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
const article = ref<ArticleDTO | null>(null)
const comments = ref<(CommentDTO & { replies?: CommentDTO[] })[]>([])
const isLiked = ref(false)
const isFavorited = ref(false)
const newComment = ref('')
const submitting = ref(false)
const loadingComments = ref(false)
const replyingTo = ref<CommentDTO | null>(null)
const replyContent = ref('')
const submittingReply = ref(false)
const parentComment = ref<CommentDTO | null>(null)

marked.setOptions({
  highlight: function(code, lang) {
    const language = hljs.getLanguage(lang) ? lang : 'plaintext'
    return hljs.highlight(code, { language }).value
  },
  breaks: true,
  gfm: true
})

const loadArticle = async () => {
  const id = Number(route.params.id)
  if (id) {
    article.value = await getArticleById(id)
    loadComments()
    checkLikeStatus()
    checkFavoriteStatus()
  }
}

const checkLikeStatus = async () => {
  if (!article.value || !userStore.isLoggedIn) return
  try {
    isLiked.value = await checkIfLiked('ARTICLE', article.value.id)
  } catch (error) {
    console.error('Failed to check like status:', error)
  }
}

const checkFavoriteStatus = async () => {
  if (!article.value || !userStore.isLoggedIn) return
  try {
    isFavorited.value = await checkIfFavorited(article.value.id)
  } catch (error) {
    console.error('Failed to check favorite status:', error)
  }
}

const loadComments = async () => {
  if (!article.value) return
  loadingComments.value = true
  try {
    const articleComments = await getCommentsByArticleId(article.value.id)
    for (const comment of articleComments) {
      const replies = await getRepliesByParentId(comment.id)
      ;(comment as any).replies = replies
    }
    comments.value = articleComments
  } catch (error) {
    console.error('Failed to load comments:', error)
  } finally {
    loadingComments.value = false
  }
}

const renderContent = (content: string) => {
  return marked.parse(content) as string
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

const toggleLike = async () => {
  if (!article.value) return
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    return
  }
  try {
    await toggleLikeApi('ARTICLE', article.value.id)
    isLiked.value = !isLiked.value
    if (isLiked.value && article.value) {
      article.value.likeCount++
    } else if (article.value && article.value.likeCount > 0) {
      article.value.likeCount--
    }
    ElMessage.success(isLiked.value ? '点赞成功' : '取消点赞')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const toggleFavorite = async () => {
  if (!article.value) return
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    return
  }
  try {
    await toggleFavoriteApi(article.value.id)
    isFavorited.value = !isFavorited.value
    ElMessage.success(isFavorited.value ? '收藏成功' : '取消收藏')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const shareArticle = () => {
  if (navigator.share) {
    navigator.share({
      title: article.value?.title,
      url: window.location.href
    })
  } else {
    navigator.clipboard.writeText(window.location.href)
    ElMessage.success('链接已复制到剪贴板')
  }
}

const submitComment = async () => {
  if (!newComment.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  if (!article.value) return
  
  submitting.value = true
  try {
    const request: CreateCommentRequest = {
      articleId: article.value.id,
      content: newComment.value
    }
    await createComment(request)
    ElMessage.success('评论成功')
    newComment.value = ''
    if (article.value) {
      article.value.commentCount++
    }
    loadComments()
  } catch (error) {
    ElMessage.error('评论失败')
  } finally {
    submitting.value = false
  }
}

const replyToComment = (comment: CommentDTO, parent?: CommentDTO) => {
  replyingTo.value = comment
  parentComment.value = parent || null
  replyContent.value = ''
}

const cancelReply = () => {
  replyingTo.value = null
  parentComment.value = null
  replyContent.value = ''
}

const submitReply = async () => {
  if (!replyContent.value.trim() || !replyingTo.value || !article.value) {
    return
  }
  
  submittingReply.value = true
  try {
    const request: CreateCommentRequest = {
      articleId: article.value.id,
      parentId: replyingTo.value.id,
      content: replyContent.value
    }
    await createComment(request)
    ElMessage.success('回复成功')
    cancelReply()
    if (article.value) {
      article.value.commentCount++
    }
    loadComments()
  } catch (error) {
    ElMessage.error('回复失败')
  } finally {
    submittingReply.value = false
  }
}

const deleteComment = async (comment: CommentDTO) => {
  try {
    await deleteCommentApi(comment.id)
    ElMessage.success('删除成功')
    if (article.value) {
      article.value.commentCount--
    }
    loadComments()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

const likeComment = async (comment: CommentDTO) => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    return
  }
  try {
    await toggleLikeApi('COMMENT', comment.id)
    comment.likeCount++
    ElMessage.success('点赞成功')
  } catch (error) {
    ElMessage.error('点赞失败')
  }
}

const canDeleteComment = (comment: CommentDTO) => {
  return userStore.user?.id === comment.userId
}

onMounted(() => {
  loadArticle()
})
</script>

<style scoped lang="scss">
.article-detail {
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

.article {
  background: #fff;
  border-radius: 8px;
  padding: 40px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.article-header {
  margin-bottom: 32px;
  padding-bottom: 24px;
  border-bottom: 1px solid #eee;
}

.article-title {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  line-height: 1.4;
  margin-bottom: 20px;
}

.article-meta {
  display: flex;
  align-items: center;
  gap: 20px;
  font-size: 14px;
  color: #999;
  flex-wrap: wrap;
  
  .meta-item {
    display: flex;
    align-items: center;
    gap: 4px;
  }
  
  .publish-time {
    margin-left: auto;
  }
}

.article-body {
  margin-bottom: 32px;
  
  .article-content {
    font-size: 16px;
    line-height: 1.8;
    color: #333;
    
    :deep(img) {
      max-width: 100%;
      height: auto;
      border-radius: 8px;
    }
    
    :deep(pre) {
      background: #f6f8fa;
      border-radius: 8px;
      padding: 16px;
      overflow-x: auto;
      
      code {
        font-family: 'Fira Code', monospace;
      }
    }
    
    :deep(code) {
      background: #f6f8fa;
      padding: 2px 6px;
      border-radius: 4px;
      font-size: 14px;
    }
    
    :deep(h1), :deep(h2), :deep(h3), :deep(h4), :deep(h5), :deep(h6) {
      margin-top: 24px;
      margin-bottom: 16px;
      font-weight: 600;
      line-height: 1.4;
    }
    
    :deep(p) {
      margin-bottom: 16px;
    }
    
    :deep(blockquote) {
      border-left: 4px solid #1890ff;
      padding-left: 16px;
      margin: 16px 0;
      color: #666;
      background: #f8f9fa;
      padding: 16px;
      border-radius: 0 8px 8px 0;
    }
    
    :deep(ul), :deep(ol) {
      margin-bottom: 16px;
      padding-left: 24px;
      
      li {
        margin-bottom: 8px;
      }
    }
    
    :deep(table) {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 16px;
      
      th, td {
        border: 1px solid #ddd;
        padding: 8px 12px;
        text-align: left;
      }
      
      th {
        background: #f6f8fa;
        font-weight: 600;
      }
    }
  }
}

.article-footer {
  padding-top: 24px;
  border-top: 1px solid #eee;
}

.article-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 24px;
  
  .tag-item {
    cursor: pointer;
  }
}

.article-actions {
  display: flex;
  gap: 12px;
}

.comment-section {
  margin-top: 24px;
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  
  .section-title {
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 24px;
  }
  
  .comment-form {
    margin-bottom: 32px;
    
    .form-actions {
      margin-top: 12px;
      display: flex;
      justify-content: flex-end;
    }
  }
  
  .comment-list {
    .comment-item {
      display: flex;
      gap: 16px;
      padding: 20px 0;
      border-bottom: 1px solid #f0f0f0;
      
      &:last-child {
        border-bottom: none;
      }
      
      .comment-avatar {
        flex-shrink: 0;
      }
      
      .comment-content-wrapper {
        flex: 1;
        min-width: 0;
        
        .comment-header {
          display: flex;
          align-items: center;
          gap: 12px;
          margin-bottom: 8px;
          
          .comment-author {
            font-weight: 600;
            color: #333;
          }
          
          .comment-time {
            font-size: 12px;
            color: #999;
          }
        }
        
        .comment-text {
          font-size: 14px;
          line-height: 1.6;
          color: #666;
          margin-bottom: 12px;
          word-wrap: break-word;
        }
        
        .comment-actions {
          display: flex;
          gap: 8px;
        }
        
        .reply-form {
          margin-top: 16px;
          padding: 16px;
          background: #f8f9fa;
          border-radius: 8px;
          
          .reply-actions {
            margin-top: 12px;
            display: flex;
            justify-content: flex-end;
            gap: 8px;
          }
        }
        
        .replies {
          margin-top: 16px;
          padding-left: 16px;
          border-left: 2px solid #f0f0f0;
          
          .reply-item {
            display: flex;
            gap: 12px;
            padding: 12px 0;
          }
        }
      }
    }
    
    .loading {
      display: flex;
      justify-content: center;
      padding: 40px 0;
    }
  }
}

.sidebar {
  width: 300px;
  flex-shrink: 0;
}

@media (max-width: 768px) {
  .content-wrapper {
    flex-direction: column;
  }
  
  .article {
    padding: 20px;
  }
  
  .article-title {
    font-size: 24px;
  }
  
  .sidebar {
    width: 100%;
  }
  
  .article-actions {
    flex-wrap: wrap;
  }
}
</style>
