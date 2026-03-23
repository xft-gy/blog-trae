import request from '@/utils/request'

export interface UserDTO {
  id: number
  username: string
  email: string
  phone: string
  nickname: string
  avatar: string
  bio: string
  status: number
  role: string
  createdTime: string
  updatedTime: string
}

export interface UpdateUserRequest {
  nickname?: string
  avatar?: string
  bio?: string
}

export interface UpdateUserRoleRequest {
  role: string
}

export interface UpdateUserStatusRequest {
  status: number
}

export const getCurrentUserProfile = (): Promise<UserDTO> => {
  return request.get('/users/profile')
}

export const updateCurrentUserProfile = (data: UpdateUserRequest): Promise<UserDTO> => {
  return request.put('/users/profile', data)
}

export const getAllUsers = (): Promise<UserDTO[]> => {
  return request.get('/users')
}

export const getUserById = (userId: number): Promise<UserDTO> => {
  return request.get(`/users/${userId}`)
}

export const updateUserRole = (userId: number, data: UpdateUserRoleRequest): Promise<void> => {
  return request.put(`/users/${userId}/role`, data)
}

export const updateUserStatus = (userId: number, data: UpdateUserStatusRequest): Promise<void> => {
  return request.put(`/users/${userId}/status`, data)
}

export const deleteUser = (userId: number): Promise<void> => {
  return request.delete(`/users/${userId}`)
}
