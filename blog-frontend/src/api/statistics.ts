import request from '@/utils/request'

export interface StatisticsDTO {
  totalUsers: number
  totalArticles: number
  totalComments: number
  totalViews: number
  todayNewUsers: number
  todayNewArticles: number
  todayNewComments: number
  todayTotalViews: number
}

export const getStatistics = (): Promise<StatisticsDTO> => {
  return request.get('/statistics')
}
