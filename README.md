# 专业知识分享博客网站

## 项目简介

一个专注于分享软件开发、AI学习及大模型应用开发等领域专业知识的博客网站。

## 技术栈

### 后端
- Java 21
- Spring Boot 3.2.x
- DDD (领域驱动设计) 架构
- MyBatis Plus
- MySQL 8.0
- Redis
- JWT认证
- SpringDoc OpenAPI (Swagger)

### 前端
- Vue 3 + TypeScript
- Element Plus
- Pinia (状态管理)
- Vue Router
- Axios
- Vite
- SCSS

## 项目结构

```
blog/
├── blog-backend/              # 后端项目
│   ├── blog-common/          # 公共模块
│   ├── blog-domain/          # 领域层
│   ├── blog-application/     # 应用层
│   ├── blog-infrastructure/  # 基础设施层
│   └── blog-interfaces/      # 接口层 (API)
├── blog-frontend/            # 前端项目
│   └── src/
│       ├── api/              # API接口
│       ├── components/       # 组件
│       ├── router/           # 路由
│       ├── stores/           # 状态管理
│       ├── styles/           # 样式
│       ├── utils/            # 工具函数
│       └── views/            # 页面
├── PLANNING.md               # 完整规划方案
├── DATABASE.md               # 数据库设计文档
└── README.md                 # 项目说明
```

## 快速开始

### 环境要求
- JDK 21+
- Node.js 18+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.8+

### 数据库初始化

1. 创建数据库并执行初始化脚本：
```bash
# 连接MySQL
mysql -u root -p

# 执行schema.sql
source blog-backend/blog-interfaces/src/main/resources/schema.sql
```

2. 修改数据库配置：
编辑 `blog-backend/blog-interfaces/src/main/resources/application.yml`，修改数据库连接信息。

### 后端启动

```bash
cd blog-backend

# 使用Maven编译
mvn clean install

# 启动应用
cd blog-interfaces
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

Swagger文档地址：http://localhost:8080/swagger-ui.html

### 前端启动

```bash
cd blog-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build
```

前端服务将在 http://localhost:3000 启动

## 核心功能

### 已实现功能
- ✅ 用户注册/登录 (JWT认证)
- ✅ 基础项目架构搭建
- ✅ DDD分层架构
- ✅ 数据库设计
- ✅ 响应式前端页面框架
- ✅ 用户状态管理

### 待实现功能
- 🔄 文章管理系统
- 🔄 分类/标签管理
- 🔄 全文搜索
- 🔄 评论系统
- 🔄 点赞/收藏
- 🔄 内容管理后台
- 🔄 AI学习助手

## 内容策略

### 核心主题分类
1. **Java开发** - Spring Boot、微服务、架构设计
2. **前端开发** - Vue.js、React、TypeScript
3. **AI学习** - 大模型基础、Prompt工程
4. **大模型应用** - LangChain、RAG、AI Agent
5. **架构设计** - 系统设计、分布式系统
6. **数据库** - MySQL、Redis、Elasticsearch

### 内容深度标准
- 入门级：基础教程、环境搭建
- 进阶级：项目实战、最佳实践
- 高级级：系统设计、源码分析

## 开发规范

### 后端开发规范
- 遵循DDD领域驱动设计
- 使用MyBatis Plus进行数据访问
- 统一返回Result格式
- 统一异常处理

### 前端开发规范
- 使用TypeScript类型安全
- 组件化开发
- 响应式设计
- 统一代码风格

## 部署

### Docker部署 (待完善)

项目支持Docker容器化部署，详见后续文档。

## 贡献指南

欢迎提交Issue和Pull Request！

## 许可证

MIT License

## 联系方式

如有问题，请提交Issue或联系项目维护者。
