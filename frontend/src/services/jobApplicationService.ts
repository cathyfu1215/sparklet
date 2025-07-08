import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'
import type { ApiResponse } from './authService'

export interface JobApplication {
  id: number
  companyName: string
  position: string
  status: string
  applicationDate: string
  lastUpdated: string
  notes?: string
}

class JobApplicationService {
  async getApplications(): Promise<ApiResponse<JobApplication[]>> {
    return await apiService.get<ApiResponse<JobApplication[]>>(API_ENDPOINTS.APPLICATIONS)
  }

  async createApplication(application: Omit<JobApplication, 'id'>): Promise<ApiResponse<JobApplication>> {
    return await apiService.post<ApiResponse<JobApplication>>(API_ENDPOINTS.APPLICATIONS, application)
  }

  async updateApplication(id: number, application: Partial<JobApplication>): Promise<ApiResponse<JobApplication>> {
    return await apiService.put<ApiResponse<JobApplication>>(`${API_ENDPOINTS.APPLICATIONS}/${id}`, application)
  }

  async deleteApplication(id: number): Promise<ApiResponse<void>> {
    return await apiService.delete<ApiResponse<void>>(`${API_ENDPOINTS.APPLICATIONS}/${id}`)
  }
}

export default new JobApplicationService()
