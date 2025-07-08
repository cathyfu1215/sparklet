import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'
import type { ApiResponse } from './authService'

export interface UserProfile {
  id: number
  email: string
  firstName: string
  lastName: string
  profilePhotoUrl: string
  createdAt: string
  updatedAt: string
}

export interface UpdateUserProfile {
  firstName: string
  lastName: string
  profilePhotoUrl: string
}

class UserService {
  async getProfile(): Promise<ApiResponse<UserProfile>> {
    return await apiService.get<ApiResponse<UserProfile>>(API_ENDPOINTS.USER_PROFILE)
  }

  async updateProfile(profile: UpdateUserProfile): Promise<ApiResponse<UserProfile>> {
    return await apiService.put<ApiResponse<UserProfile>>(API_ENDPOINTS.USER_PROFILE, profile)
  }
}

export default new UserService()
