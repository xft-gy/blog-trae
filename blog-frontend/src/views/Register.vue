<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-card">
        <h2 class="register-title">注册</h2>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input 
              v-model="form.username" 
              placeholder="用户名 (3-50个字符)" 
              size="large"
              :prefix-icon="User"
            />
          </el-form-item>
          <el-form-item prop="email">
            <el-input 
              v-model="form.email" 
              type="email"
              placeholder="邮箱" 
              size="large"
              :prefix-icon="Message"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input 
              v-model="form.password" 
              type="password" 
              placeholder="密码 (至少6个字符)" 
              size="large"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input 
              v-model="confirmPassword" 
              type="password" 
              placeholder="确认密码" 
              size="large"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>
          <el-form-item>
            <el-button 
              type="primary" 
              size="large" 
              style="width: 100%" 
              :loading="loading"
              @click="handleRegister"
            >
              注册
            </el-button>
          </el-form-item>
        </el-form>
        <div class="register-footer">
          <span>已有账号？</span>
          <router-link to="/login">立即登录</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, Message } from '@element-plus/icons-vue'
import { register, type RegisterRequest } from '@/api/auth'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const confirmPassword = ref('')

const form = reactive<RegisterRequest>({
  username: '',
  email: '',
  password: ''
})

const validateConfirmPassword = (rule: any, value: any, callback: any) => {
  if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '用户名长度在 3 到 50 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  await formRef.value?.validate()
  loading.value = true
  try {
    await register(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.register-container {
  width: 100%;
  max-width: 400px;
}

.register-card {
  background: #fff;
  border-radius: 16px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.register-title {
  text-align: center;
  margin-bottom: 32px;
  font-size: 28px;
  color: #333;
}

.register-footer {
  text-align: center;
  margin-top: 16px;
  
  span {
    color: #666;
  }
  
  a {
    color: #1890ff;
    
    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
