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

export function getCurrentUserProfile() {
  return request.get<UserDTO>('/api/users/profile')
}

export function updateCurrentUserProfile(data: UpdateUserRequest) {
  return request.put('/api/users/profile', data)
}

export function getAllUsers() {
  return request.get<UserDTO[]>('/api/users')
}

export function getUserById(userId: number) {
  return request.get<UserDTO>(`/api/users/${userId}`)
}

export function updateUserRole(userId: number, data: UpdateUserRoleRequest) {
  return request.put(`/api/users/${userId}/role`, data)
}

export function updateUserStatus(userId: number, data: UpdateUserStatusRequest) {
  return request.put(`/api/users/${userId}/status`, data)
}

export function deleteUser(userId: number) {
  return request.delete(`/api/users/${userId}`)
}
