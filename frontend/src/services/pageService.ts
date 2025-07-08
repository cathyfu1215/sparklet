import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'
import type { ApiResponse } from './authService'

export interface PageInfo {
  pageName: string
  description: string
  status: string
  comingSoon: string
}

class PageService {
  async getPageInfo(pageId: string): Promise<ApiResponse<PageInfo>> {
    return await apiService.get<ApiResponse<PageInfo>>(`${API_ENDPOINTS.PAGE_INFO}/${pageId}`)
  }

  async getAllPageInfo(): Promise<ApiResponse<Record<string, PageInfo>>> {
    return await apiService.get<ApiResponse<Record<string, PageInfo>>>(API_ENDPOINTS.PAGES)
  }
}

export default new PageService()
