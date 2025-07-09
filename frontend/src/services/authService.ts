import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'

export interface LoginRequest {
  email: string
  password: string
}

export interface SignupRequest {
  email: string
  password: string
  firstName: string
  lastName: string
}

export interface AuthResponse {
  success: boolean
  message: string
  data: {
    token: string
    type: string
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
  }
}

export interface ApiResponse<T> {
  success: boolean
  message: string
  data: T
}

class AuthService {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    return await apiService.post<AuthResponse>(API_ENDPOINTS.LOGIN, credentials)
  }

  async signup(userData: SignupRequest): Promise<AuthResponse> {
    return await apiService.post<AuthResponse>(API_ENDPOINTS.SIGNUP, userData)
  }

  logout(): void {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  getToken(): string | null {
    const token = localStorage.getItem('token')
    if (!token || token === 'undefined' || token === 'null') {
      return null
    }
    return token
  }

  getUser(): any {
    const userStr = localStorage.getItem('user')
    if (!userStr || userStr === 'undefined' || userStr === 'null') {
      return null
    }
    try {
      return JSON.parse(userStr)
    } catch (error) {
      console.error('Error parsing user from localStorage:', error)
      // Clear corrupted data
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      return null
    }
  }

  isAuthenticated(): boolean {
    return !!this.getToken()
  }
}

export default new AuthService()
