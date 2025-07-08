import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'
import type { ApiResponse } from './authService'

export interface DashboardStats {
  totalApplications: number
  activeApplications: number
  interviewsScheduled: number
  practiceProblemsCompleted: number
  feedbackReceived: number
  preparationScore: number
}

class DashboardService {
  async getStats(): Promise<ApiResponse<DashboardStats>> {
    return await apiService.get<ApiResponse<DashboardStats>>(API_ENDPOINTS.DASHBOARD_STATS)
  }
}

export default new DashboardService()
