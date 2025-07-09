import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'
import type { ApiResponse } from './authService'

export interface UserProfile {
  id: number
  email: string
  firstName: string
  lastName: string
  profilePhotoUrl: string
  mbtiType?: string
  linkedinUrl?: string
  personalWebsiteUrl?: string
  githubUrl?: string
  accountType: 'BASIC' | 'PREMIUM'
  premiumExpiryDate?: string
  createdAt: string
  updatedAt: string
}

export interface UpdateUserProfile {
  firstName: string
  lastName: string
  profilePhotoUrl: string
  mbtiType?: string
  linkedinUrl?: string
  personalWebsiteUrl?: string
  githubUrl?: string
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
