import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { LoginResponse } from '@/api/auth'
import { getCurrentUserProfile, type UserDTO } from '@/api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<LoginResponse | null>(
    localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')!) : null
  )

  const isLoggedIn = computed(() => !!token.value)
  const isAdmin = computed(() => userInfo.value?.role === 'ADMIN')

  const setUser = (data: LoginResponse) => {
    token.value = data.token
    userInfo.value = data
    localStorage.setItem('token', data.token)
    localStorage.setItem('userInfo', JSON.stringify(data))
  }

  const fetchUserInfo = async () => {
    try {
      const user: UserDTO = await getCurrentUserProfile()
      if (userInfo.value) {
        userInfo.value.nickname = user.nickname
        userInfo.value.avatar = user.avatar
        localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
      }
    } catch (error) {
      console.error('Failed to fetch user info:', error)
    }
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    isAdmin,
    setUser,
    fetchUserInfo,
    logout
  }
})
