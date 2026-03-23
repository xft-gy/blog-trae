import request from '@/utils/request'

export interface UploadResponse {
  url: string
  filename: string
  path: string
}

export const uploadFile = (file: File): Promise<UploadResponse> => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/files/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const uploadImage = (file: File): Promise<UploadResponse> => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/files/upload/image', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
