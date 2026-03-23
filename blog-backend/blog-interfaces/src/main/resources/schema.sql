-- 创建数据库
CREATE DATABASE IF NOT EXISTS blog_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE blog_db;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    bio VARCHAR(500) COMMENT '个人简介',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    role VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色：USER-普通用户，ADMIN-管理员',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 分类表
CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    slug VARCHAR(50) NOT NULL UNIQUE COMMENT '分类标识',
    description VARCHAR(200) COMMENT '分类描述',
    icon VARCHAR(255) COMMENT '分类图标',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序序号',
    article_count INT NOT NULL DEFAULT 0 COMMENT '文章数量',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_slug (slug),
    INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- 3. 标签表
CREATE TABLE IF NOT EXISTS tag (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
    slug VARCHAR(50) NOT NULL UNIQUE COMMENT '标签标识',
    color VARCHAR(20) COMMENT '标签颜色',
    article_count INT NOT NULL DEFAULT 0 COMMENT '文章数量',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 4. 文章表
CREATE TABLE IF NOT EXISTS article (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    title VARCHAR(200) NOT NULL COMMENT '文章标题',
    summary VARCHAR(500) COMMENT '文章摘要',
    content LONGTEXT NOT NULL COMMENT '文章内容（Markdown）',
    content_html LONGTEXT COMMENT '文章内容（HTML）',
    cover_image VARCHAR(255) COMMENT '封面图URL',
    author_id BIGINT NOT NULL COMMENT '作者ID',
    category_id BIGINT COMMENT '分类ID',
    view_count INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    like_count INT NOT NULL DEFAULT 0 COMMENT '点赞次数',
    comment_count INT NOT NULL DEFAULT 0 COMMENT '评论次数',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-草稿，1-已发布，2-已下线',
    is_top TINYINT NOT NULL DEFAULT 0 COMMENT '是否置顶：0-否，1-是',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    published_time DATETIME COMMENT '发布时间',
    INDEX idx_author_id (author_id),
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_published_time (published_time),
    INDEX idx_view_count (view_count DESC),
    INDEX idx_is_top (is_top DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- 5. 文章标签关联表
CREATE TABLE IF NOT EXISTS article_tag (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    article_id BIGINT NOT NULL COMMENT '文章ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_article_id (article_id),
    INDEX idx_tag_id (tag_id),
    UNIQUE KEY uk_article_tag (article_id, tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章标签关联表';

-- 6. 评论表
CREATE TABLE IF NOT EXISTS comment (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    article_id BIGINT NOT NULL COMMENT '文章ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    parent_id BIGINT COMMENT '父评论ID',
    content TEXT NOT NULL COMMENT '评论内容',
    like_count INT NOT NULL DEFAULT 0 COMMENT '点赞次数',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-待审核，1-已通过，2-已拒绝',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_article_id (article_id),
    INDEX idx_user_id (user_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- 7. 用户点赞表
CREATE TABLE IF NOT EXISTS user_like (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    target_type VARCHAR(20) NOT NULL COMMENT '目标类型：ARTICLE-文章，COMMENT-评论',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_target (user_id, target_type, target_id),
    INDEX idx_target (target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户点赞表';

-- 8. 用户收藏表
CREATE TABLE IF NOT EXISTS user_favorite (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    article_id BIGINT NOT NULL COMMENT '文章ID',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_article (user_id, article_id),
    INDEX idx_article_id (article_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户收藏表';

-- 9. 搜索记录表
CREATE TABLE IF NOT EXISTS search_history (
    id BIGINT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT COMMENT '用户ID',
    keyword VARCHAR(200) NOT NULL COMMENT '搜索关键词',
    search_count INT NOT NULL DEFAULT 1 COMMENT '搜索次数',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_keyword (keyword)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='搜索记录表';

-- 插入初始分类数据
INSERT INTO category (id, name, slug, description, sort_order) VALUES
(1, 'Java开发', 'java', 'Java后端开发相关内容', 1),
(2, '前端开发', 'frontend', '前端开发相关内容', 2),
(3, 'AI学习', 'ai-learning', '人工智能学习相关内容', 3),
(4, '大模型应用', 'llm-app', '大模型应用开发相关内容', 4),
(5, '架构设计', 'architecture', '系统架构设计相关内容', 5),
(6, '数据库', 'database', '数据库技术相关内容', 6);

-- 插入初始标签数据
INSERT INTO tag (id, name, slug, color) VALUES
(1, 'Spring Boot', 'spring-boot', '#1890FF'),
(2, 'Vue.js', 'vuejs', '#41B883'),
(3, 'React', 'react', '#61DAFB'),
(4, 'MyBatis', 'mybatis', '#FF6700'),
(5, 'MySQL', 'mysql', '#00758F'),
(6, 'Redis', 'redis', '#DC382D'),
(7, 'LangChain', 'langchain', '#00FF00'),
(8, 'ChatGPT', 'chatgpt', '#74AA9C'),
(9, '微服务', 'microservices', '#FFC107'),
(10, 'Docker', 'docker', '#2496ED');

-- 插入默认管理员用户（密码：admin123，使用BCrypt加密）
INSERT INTO sys_user (id, username, password, email, nickname, status, role) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'admin@blog.com', '管理员', 1, 'ADMIN');
