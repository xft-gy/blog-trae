import request from '@/utils/request'

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  password: string
  email: string
}

export interface LoginResponse {
  token: string
  userId: number
  username: string
  nickname: string
  avatar?: string
  role: string
}

export const login = (data: LoginRequest): Promise<LoginResponse> => {
  return request.post('/auth/login', data)
}

export const register = (data: RegisterRequest): Promise<void> => {
  return request.post('/auth/register', data)
}
