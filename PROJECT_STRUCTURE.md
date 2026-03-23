# 项目结构总览

## 完整目录结构

```
blog/
│
├── PLANNING.md                    # 完整规划方案
├── DATABASE.md                    # 数据库设计文档
├── CONTENT_PLAN.md                # 内容运营计划
├── PROJECT_STRUCTURE.md           # 项目结构说明（本文件）
├── README.md                      # 项目说明文档
├── docker-compose.yml             # Docker Compose配置
│
├── blog-backend/                  # 后端项目 (Java + DDD)
│   ├── pom.xml                    # 父POM文件
│   ├── Dockerfile                 # 后端Dockerfile
│   ├── .dockerignore              # Docker忽略文件
│   │
│   ├── blog-common/               # 公共模块
│   │   ├── pom.xml
│   │   └── src/main/java/com/blog/common/
│   │       ├── annotation/        # 注解
│   │       │   ├── RequireAdmin.java
│   │       │   └── RequireLogin.java
│   │       ├── constant/          # 常量定义
│   │       │   └── CommonConstants.java
│   │       ├── exception/         # 异常处理
│   │       │   ├── BusinessException.java
│   │       │   └── GlobalExceptionHandler.java
│   │       ├── result/            # 统一返回结果
│   │       │   └── Result.java
│   │       └── util/              # 工具类
│   │           └── JwtUtil.java
│   │
│   ├── blog-domain/               # 领域层
│   │   ├── pom.xml
│   │   └── src/main/java/com/blog/domain/
│   │       ├── user/              # 用户领域
│   │       │   ├── entity/
│   │       │   │   └── User.java
│   │       │   └── repository/
│   │       │       └── UserRepository.java
│   │       └── article/           # 文章领域
│   │           ├── entity/
│   │           │   ├── Article.java
│   │           │   ├── Category.java
│   │           │   ├── Tag.java
│   │           │   ├── Comment.java
│   │           │   ├── UserLike.java
│   │           │   └── UserFavorite.java
│   │           └── repository/
│   │               ├── ArticleRepository.java
│   │               ├── CommentRepository.java
│   │               ├── CategoryRepository.java
│   │               ├── TagRepository.java
│   │               ├── UserLikeRepository.java
│   │               └── UserFavoriteRepository.java
│   │
│   ├── blog-application/          # 应用层
│   │   ├── pom.xml
│   │   └── src/main/java/com/blog/application/
│   │       ├── user/              # 用户应用服务
│   │       │   ├── dto/
│   │       │   │   ├── RegisterRequest.java
│   │       │   │   ├── LoginRequest.java
│   │       │   │   ├── LoginResponse.java
│   │       │   │   ├── UserDTO.java
│   │       │   │   ├── UpdateUserRequest.java
│   │       │   │   ├── UpdateUserRoleRequest.java
│   │       │   │   └── UpdateUserStatusRequest.java
│   │       │   └── service/
│   │       │       └── UserService.java
│   │       └── article/           # 文章应用服务
│   │           ├── dto/
│   │           │   ├── ArticleDTO.java
│   │           │   ├── CreateArticleRequest.java
│   │           │   ├── UpdateArticleRequest.java
│   │           │   ├── CommentDTO.java
│   │           │   ├── CreateCommentRequest.java
│   │           │   ├── CategoryDTO.java
│   │           │   ├── CreateCategoryRequest.java
│   │           │   ├── TagDTO.java
│   │           │   ├── CreateTagRequest.java
│   │           │   ├── StatisticsDTO.java
│   │           │   └── PerformanceMetricsDTO.java
│   │           └── service/
│   │               ├── ArticleService.java
│   │               ├── CommentService.java
│   │               ├── CategoryService.java
│   │               ├── TagService.java
│   │               ├── LikeService.java
│   │               ├── FavoriteService.java
│   │               ├── StatisticsService.java
│   │               └── PerformanceService.java
│   │
│   ├── blog-infrastructure/       # 基础设施层
│   │   ├── pom.xml
│   │   └── src/main/java/com/blog/infrastructure/
│   │       ├── config/            # 配置
│   │       │   └── MybatisPlusConfig.java
│   │       └── persistence/       # 持久化
│   │           ├── entity/         # 数据库实体
│   │           │   ├── UserDO.java
│   │           │   ├── ArticleDO.java
│   │           │   ├── CategoryDO.java
│   │           │   ├── TagDO.java
│   │           │   ├── CommentDO.java
│   │           │   ├── UserLikeDO.java
│   │           │   └── UserFavoriteDO.java
│   │           ├── mapper/         # MyBatis Plus Mapper
│   │           │   ├── UserMapper.java
│   │           │   ├── ArticleMapper.java
│   │           │   ├── CategoryMapper.java
│   │           │   ├── TagMapper.java
│   │           │   ├── CommentMapper.java
│   │           │   ├── UserLikeMapper.java
│   │           │   └── UserFavoriteMapper.java
│   │           └── repository/     # Repository实现
│   │               ├── UserRepositoryImpl.java
│   │               ├── ArticleRepositoryImpl.java
│   │               ├── CommentRepositoryImpl.java
│   │               ├── CategoryRepositoryImpl.java
│   │               ├── TagRepositoryImpl.java
│   │               ├── UserLikeRepositoryImpl.java
│   │               └── UserFavoriteRepositoryImpl.java
│   │
│   └── blog-interfaces/           # 接口层 (API)
│       ├── pom.xml
│       ├── src/main/java/com/blog/
│       │   ├── BlogApplication.java  # 启动类
│       │   └── interfaces/
│       │       ├── api/              # API控制器
│       │       │   ├── HealthController.java
│       │       │   ├── AuthController.java
│       │       │   ├── ArticleController.java
│       │       │   ├── CommentController.java
│       │       │   ├── CategoryController.java
│       │       │   ├── TagController.java
│       │       │   ├── InteractionController.java
│       │       │   ├── UserController.java
│       │       │   ├── FileController.java
│       │       │   ├── StatisticsController.java
│       │       │   └── PerformanceController.java
│       │       ├── aspect/           # AOP切面
│       │       │   └── PermissionAspect.java
│       │       └── config/           # 配置
│       │           ├── JwtAuthenticationFilter.java
│       │           ├── WebConfig.java
│       │           └── PerformanceInterceptor.java
│       └── src/main/resources/
│           ├── application.yml      # 配置文件
│           └── schema.sql           # 数据库初始化脚本
│
└── blog-frontend/                 # 前端项目 (Vue 3 + TypeScript)
    ├── package.json
    ├── vite.config.ts
    ├── tsconfig.json
    ├── tsconfig.node.json
    ├── index.html
    ├── Dockerfile                 # 前端Dockerfile
    ├── nginx.conf                 # Nginx配置
    ├── .dockerignore              # Docker忽略文件
    │
    └── src/
        ├── main.ts                  # 入口文件
        ├── App.vue                  # 根组件
        ├── router/                  # 路由
        │   └── index.ts
        ├── stores/                  # 状态管理 (Pinia)
        │   └── user.ts
        ├── api/                     # API接口
        │   ├── auth.ts
        │   ├── article.ts
        │   ├── category.ts
        │   ├── user.ts
        │   ├── file.ts
        │   ├── statistics.ts
        │   └── performance.ts
        ├── utils/                   # 工具函数
        │   └── request.ts
        ├── styles/                  # 全局样式
        │   └── index.scss
        ├── components/              # 组件
        │   └── Header.vue
        ├── layouts/                 # 布局
        │   └── AdminLayout.vue
        └── views/                   # 页面
            ├── Home.vue
            ├── Login.vue
            ├── Register.vue
            ├── ArticleDetail.vue
            ├── Category.vue
            ├── Search.vue
            ├── Profile.vue
            └── admin/
                ├── ArticleManage.vue
                ├── CategoryManage.vue
                ├── TagManage.vue
                ├── CommentManage.vue
                ├── UserManage.vue
                ├── StatisticsDashboard.vue
                └── PerformanceMonitor.vue
```

## 技术架构说明

### 后端架构 (DDD 领域驱动设计)

#### 1. Common 层 (公共模块)
- 统一返回结果 `Result<T>`
- 业务异常 `BusinessException`
- 全局异常处理器 `GlobalExceptionHandler`
- JWT工具类 `JwtUtil`
- 常量定义 `CommonConstants`
- 权限注解 `RequireLogin`, `RequireAdmin`

#### 2. Domain 层 (领域层)
- 领域实体 (Entity)
- 领域服务 (Domain Service)
- 仓储接口 (Repository Interface)
- 领域事件 (Domain Event)

#### 3. Application 层 (应用层)
- 应用服务 (Application Service)
- DTO (数据传输对象)
- 命令/查询对象 (Command/Query)

#### 4. Infrastructure 层 (基础设施层)
- 持久化实现 (Repository Implementation)
- 第三方服务集成
- 配置类 (Configuration)
- 数据库实体 (DO - Data Object)

#### 5. Interfaces 层 (接口层)
- REST API 控制器 (Controller)
- 请求/响应对象 (Request/Response)
- 参数校验 (Validation)
- AOP切面 (权限验证)
- 拦截器 (性能监控)

### 前端架构

#### 核心技术栈
- Vue 3 (Composition API)
- TypeScript
- Element Plus (UI组件库)
- Pinia (状态管理)
- Vue Router (路由)
- Axios (HTTP客户端)
- Vite (构建工具)
- SCSS (CSS预处理器)

#### 目录结构说明
- `api/`: API接口定义
- `components/`: 可复用组件
- `layouts/`: 布局组件
- `router/`: 路由配置
- `stores/`: Pinia状态管理
- `styles/`: 全局样式
- `utils/`: 工具函数
- `views/`: 页面组件

## 数据库设计

### 核心数据表
1. `sys_user` - 用户表
2. `article` - 文章表
3. `category` - 分类表
4. `tag` - 标签表
5. `article_tag` - 文章标签关联表
6. `comment` - 评论表
7. `user_like` - 用户点赞表
8. `user_favorite` - 用户收藏表
9. `search_history` - 搜索记录表

详细设计请参考 [DATABASE.md](./DATABASE.md)

## 已实现功能 (✅)

### 后端功能
- ✅ 用户注册/登录 (JWT认证)
- ✅ 统一异常处理
- ✅ 统一返回格式
- ✅ 文章CRUD操作
- ✅ 文章列表查询
- ✅ 文章搜索（基础SQL搜索 + 高级搜索）
- ✅ 分类/标签CRUD
- ✅ 评论系统CRUD（支持嵌套回复）
- ✅ 点赞/收藏功能
- ✅ Swagger API文档
- ✅ 用户权限管理（普通用户/管理员）
- ✅ 草稿保存功能
- ✅ 文章审核功能（发布/下线）
- ✅ 文件上传功能（图片、附件等）
- ✅ 数据统计功能（访问量、用户增长、文章热度等）
- ✅ 性能监控功能
- ✅ Docker容器化部署
- ✅ 搜索服务接口（为Elasticsearch集成预留）

### 前端功能
- ✅ 项目初始化和配置
- ✅ 路由配置
- ✅ 状态管理 (Pinia)
- ✅ HTTP请求封装
- ✅ 用户登录/注册页面
- ✅ 首页 (文章列表)
- ✅ 文章详情页
- ✅ Markdown渲染
- ✅ 代码高亮
- ✅ 响应式设计
- ✅ 用户信息展示
- ✅ 搜索页面（支持高级搜索、分类筛选、排序、高亮显示）
- ✅ 评论系统（完整的前端交互）
- ✅ 内容管理后台（文章、分类、标签、评论、用户管理、数据统计、性能监控）
- ✅ 用户个人中心（支持编辑个人信息）
- ✅ 点赞/收藏功能
- ✅ 响应式设计和移动端适配
- ✅ 用户权限管理（管理员/普通用户区分）
- ✅ 草稿保存和管理
- ✅ 文章审核功能（发布/下线）
- ✅ Docker容器化部署

## 待实现功能 (🔄)

### 中优先级
- 🔄 全文搜索 (Elasticsearch)
- 🔄 第三方登录（GitHub、微信等）
- 🔄 邮件通知（评论通知、系统通知等）
- 🔄 搜索建议和搜索历史

### 低优先级
- 🔄 AI学习助手集成（AI对话、代码解释等）
- 🔄 会员系统
- 🔄 付费功能（付费专栏、课程等）
- 🔄 社区论坛

## 部署架构

### 开发环境
- 后端: Spring Boot 3.x + Java 21
- 前端: Vite Dev Server
- 数据库: MySQL 8.0
- 缓存: Redis

### 生产环境
- ✅ 容器化: Docker + Docker Compose
- ✅ 反向代理: Nginx（前端配置）
- CI/CD: GitHub Actions（待实现）
- 监控: Prometheus + Grafana（待实现）

## Docker 部署说明

### 使用 Docker Compose 一键部署

```bash
# 在项目根目录下执行
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down

# 停止并删除数据卷（谨慎使用）
docker-compose down -v
```

### 服务访问地址
- 前端: http://localhost
- 后端: http://localhost:8080
- 后端API文档: http://localhost:8080/swagger-ui.html
- MySQL: localhost:3306
- Redis: localhost:6379

### 默认管理员账号
- 用户名: admin
- 密码: admin123

详细信息请参考 [README.md](./README.md)
