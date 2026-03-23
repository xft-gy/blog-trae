import request from '@/utils/request'

export interface PerformanceMetricsDTO {
  cpuUsage: number
  memoryUsage: number
  jvmMemoryUsed: number
  jvmMemoryMax: number
  activeThreads: number
  uptimeSeconds: number
  totalRequests: number
  averageResponseTime: number
  requestsPerSecond: number
}

export const getPerformanceMetrics = (): Promise<PerformanceMetricsDTO> => {
  return request.get('/performance')
}
