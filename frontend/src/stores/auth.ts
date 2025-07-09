import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import authService from '../services/authService'
import type { LoginRequest, SignupRequest } from '../services/authService'

export const useAuthStore = defineStore('auth', () => {
  // State - Initialize with proper types
  const user = ref<any>(null)
  const token = ref<string | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  // Initialize auth state safely
  function initializeAuth() {
    try {
      const storedUser = authService.getUser()
      const storedToken = authService.getToken()
      
      if (storedUser && storedToken) {
        user.value = storedUser
        token.value = storedToken
      }
    } catch (err) {
      console.error('Error initializing auth:', err)
      // Clear any corrupted data
      localStorage.removeItem('user')
      localStorage.removeItem('token')
    }
  }

  // Call initialization
  initializeAuth()

  // Getters
  const isAuthenticated = computed(() => !!token.value)
  const currentUser = computed(() => user.value)

  // Actions
  async function login(credentials: LoginRequest) {
    loading.value = true
    error.value = null
    
    try {
      const response = await authService.login(credentials)
      
      if (response.success) {
        token.value = response.data.token
        // Backend returns user data directly in data, not nested under user
        user.value = {
          id: response.data.id,
          email: response.data.email,
          firstName: response.data.firstName,
          lastName: response.data.lastName,
          profilePhotoUrl: response.data.profilePhotoUrl,
          mbtiType: response.data.mbtiType,
          linkedinUrl: response.data.linkedinUrl,
          personalWebsiteUrl: response.data.personalWebsiteUrl,
          githubUrl: response.data.githubUrl,
          accountType: response.data.accountType,
          premiumExpiryDate: response.data.premiumExpiryDate
        }
        
        // Store in localStorage
        localStorage.setItem('token', response.data.token)
        localStorage.setItem('user', JSON.stringify(user.value))
        
        return { success: true }
      } else {
        error.value = response.message
        return { success: false, message: response.message }
      }
    } catch (err: any) {
      console.error('Login error:', err)
      error.value = err.response?.data?.message || 'Login failed'
      return { success: false, message: error.value }
    } finally {
      loading.value = false
    }
  }

  async function signup(userData: SignupRequest) {
    loading.value = true
    error.value = null
    
    try {
      const response = await authService.signup(userData)
      
      if (response.success) {
        // Backend signup doesn't return token/user, just success message
        // After successful signup, automatically login
        return await login({ email: userData.email, password: userData.password })
      } else {
        error.value = response.message
        return { success: false, message: response.message }
      }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Signup failed'
      return { success: false, message: error.value }
    } finally {
      loading.value = false
    }
  }

  function logout() {
    authService.logout()
    token.value = null
    user.value = null
    error.value = null
  }

  function clearError() {
    error.value = null
  }

  function clearCorruptedData() {
    // Clear any corrupted localStorage data
    const userStr = localStorage.getItem('user')
    const tokenStr = localStorage.getItem('token')
    
    if (userStr === 'undefined' || userStr === 'null' || tokenStr === 'undefined' || tokenStr === 'null') {
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      user.value = null
      token.value = null
    }
  }

  // Clear corrupted data on store initialization
  clearCorruptedData()

  return {
    // State
    user,
    token,
    loading,
    error,
    // Getters
    isAuthenticated,
    currentUser,
    // Actions
    login,
    signup,
    logout,
    clearError
  }
})