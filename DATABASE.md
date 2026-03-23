# 数据库设计文档

## 数据库表结构

### 1. 用户表 (sys_user)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| username | varchar | 50 | NO | - | 用户名 |
| password | varchar | 255 | NO | - | 密码（加密） |
| email | varchar | 100 | YES | NULL | 邮箱 |
| phone | varchar | 20 | YES | NULL | 手机号 |
| nickname | varchar | 50 | YES | NULL | 昵称 |
| avatar | varchar | 255 | YES | NULL | 头像URL |
| bio | varchar | 500 | YES | NULL | 个人简介 |
| status | tinyint | - | NO | 1 | 状态：0-禁用，1-启用 |
| role | varchar | 20 | NO | 'USER' | 角色：USER-普通用户，ADMIN-管理员 |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |
| updated_time | datetime | - | NO | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2. 文章表 (article)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| title | varchar | 200 | NO | - | 文章标题 |
| summary | varchar | 500 | YES | NULL | 文章摘要 |
| content | longtext | - | NO | - | 文章内容（Markdown） |
| content_html | longtext | - | YES | NULL | 文章内容（HTML） |
| cover_image | varchar | 255 | YES | NULL | 封面图URL |
| author_id | bigint | - | NO | - | 作者ID |
| category_id | bigint | - | YES | NULL | 分类ID |
| view_count | int | - | NO | 0 | 浏览次数 |
| like_count | int | - | NO | 0 | 点赞次数 |
| comment_count | int | - | NO | 0 | 评论次数 |
| status | tinyint | - | NO | 0 | 状态：0-草稿，1-已发布，2-已下线 |
| is_top | tinyint | - | NO | 0 | 是否置顶：0-否，1-是 |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |
| updated_time | datetime | - | NO | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| published_time | datetime | - | YES | NULL | 发布时间 |

### 3. 分类表 (category)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| name | varchar | 50 | NO | - | 分类名称 |
| slug | varchar | 50 | NO | - | 分类标识（URL友好） |
| description | varchar | 200 | YES | NULL | 分类描述 |
| icon | varchar | 255 | YES | NULL | 分类图标 |
| sort_order | int | - | NO | 0 | 排序序号 |
| article_count | int | - | NO | 0 | 文章数量 |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |
| updated_time | datetime | - | NO | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 4. 标签表 (tag)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| name | varchar | 50 | NO | - | 标签名称 |
| slug | varchar | 50 | NO | - | 标签标识（URL友好） |
| color | varchar | 20 | YES | NULL | 标签颜色 |
| article_count | int | - | NO | 0 | 文章数量 |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |
| updated_time | datetime | - | NO | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 5. 文章标签关联表 (article_tag)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| article_id | bigint | - | NO | - | 文章ID |
| tag_id | bigint | - | NO | - | 标签ID |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |

### 6. 评论表 (comment)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| article_id | bigint | - | NO | - | 文章ID |
| user_id | bigint | - | NO | - | 用户ID |
| parent_id | bigint | - | YES | NULL | 父评论ID（回复时） |
| content | text | - | NO | - | 评论内容 |
| like_count | int | - | NO | 0 | 点赞次数 |
| status | tinyint | - | NO | 1 | 状态：0-待审核，1-已通过，2-已拒绝 |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |
| updated_time | datetime | - | NO | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 7. 用户点赞表 (user_like)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| user_id | bigint | - | NO | - | 用户ID |
| target_type | varchar | 20 | NO | - | 目标类型：ARTICLE-文章，COMMENT-评论 |
| target_id | bigint | - | NO | - | 目标ID |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |

### 8. 用户收藏表 (user_favorite)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| user_id | bigint | - | NO | - | 用户ID |
| article_id | bigint | - | NO | - | 文章ID |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |

### 9. 搜索记录表 (search_history)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | - | NO | - | 主键ID |
| user_id | bigint | - | YES | NULL | 用户ID（游客为NULL） |
| keyword | varchar | 200 | NO | - | 搜索关键词 |
| search_count | int | - | NO | 1 | 搜索次数 |
| created_time | datetime | - | NO | CURRENT_TIMESTAMP | 创建时间 |
| updated_time | datetime | - | NO | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

## 索引设计

```sql
-- 用户表索引
CREATE INDEX idx_username ON sys_user(username);
CREATE INDEX idx_email ON sys_user(email);
CREATE INDEX idx_phone ON sys_user(phone);

-- 文章表索引
CREATE INDEX idx_author_id ON article(author_id);
CREATE INDEX idx_category_id ON article(category_id);
CREATE INDEX idx_status ON article(status);
CREATE INDEX idx_published_time ON article(published_time);
CREATE INDEX idx_view_count ON article(view_count DESC);
CREATE INDEX idx_is_top ON article(is_top DESC);

-- 分类表索引
CREATE INDEX idx_slug ON category(slug);
CREATE INDEX idx_sort_order ON category(sort_order);

-- 标签表索引
CREATE INDEX idx_slug ON tag(slug);

-- 文章标签关联表索引
CREATE INDEX idx_article_id ON article_tag(article_id);
CREATE INDEX idx_tag_id ON article_tag(tag_id);

-- 评论表索引
CREATE INDEX idx_article_id ON comment(article_id);
CREATE INDEX idx_user_id ON comment(user_id);
CREATE INDEX idx_parent_id ON comment(parent_id);
CREATE INDEX idx_status ON comment(status);

-- 用户点赞表索引
CREATE UNIQUE INDEX uk_user_target ON user_like(user_id, target_type, target_id);
CREATE INDEX idx_target ON user_like(target_type, target_id);

-- 用户收藏表索引
CREATE UNIQUE INDEX uk_user_article ON user_favorite(user_id, article_id);
CREATE INDEX idx_article_id ON user_favorite(article_id);

-- 搜索记录表索引
CREATE INDEX idx_user_id ON search_history(user_id);
CREATE INDEX idx_keyword ON search_history(keyword);
```
