<template>
  <div class="min-h-screen bg-gradient-to-br from-macaroon-peach-50 via-macaroon-vanilla-50 to-macaroon-lavender-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900">
    <!-- Navigation Header -->
    <nav class="bg-white/90 dark:bg-gray-800/90 backdrop-blur-md shadow-soft border-b border-macaroon-peach-100 dark:border-gray-700">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between items-center h-16">
          <!-- Logo -->
          <router-link to="/" class="flex items-center">
            <h1 class="text-2xl font-bold bg-gradient-to-r from-macaroon-peach-400 to-macaroon-lavender-400 bg-clip-text text-transparent">
              Sparklet
            </h1>
          </router-link>

          <!-- Navigation Links -->
          <div class="flex items-center space-x-4">
            <router-link 
              to="/" 
              class="nav-link"
            >
              Back to Home
            </router-link>
            <router-link 
              to="/signup" 
              class="btn btn-primary"
            >
              Sign Up
            </router-link>
          </div>
        </div>
      </div>
    </nav>

    <div class="flex items-center justify-center p-4 min-h-[calc(100vh-4rem)]">
      <div class="w-full max-w-md">
        <div class="text-center mb-8">
          <h1 class="text-3xl font-bold bg-gradient-to-r from-macaroon-peach-500 to-macaroon-lavender-500 bg-clip-text text-transparent mb-2">
            Welcome Back
          </h1>
          <p class="text-gray-600 dark:text-gray-400">Sign in to continue your job search journey</p>
        </div>
      
      <form @submit.prevent="handleLogin" class="card">
        <div class="card-body">
          <!-- Error Message -->
          <div v-if="authStore.error" class="alert alert-error mb-6">
            {{ authStore.error }}
          </div>
          
          <div class="form-group">
            <label class="form-label" for="email">
              Email Address
            </label>
            <input 
              v-model="form.email"
              class="form-control" 
              id="email" 
              type="email" 
              placeholder="your@email.com"
              required
            >
          </div>
          
          <div class="form-group">
            <label class="form-label" for="password">
              Password
            </label>
            <div class="relative">
              <input 
                v-model="form.password"
                class="form-control pr-12" 
                id="password" 
                :type="showPassword ? 'text' : 'password'" 
                placeholder="Enter your password"
                required
              >
              <button 
                type="button"
                @click="showPassword = !showPassword"
                class="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-macaroon-peach-600 dark:text-gray-500 dark:hover:text-macaroon-peach-400 transition-colors duration-200"
                aria-label="Toggle password visibility"
              >
                <svg v-if="showPassword" class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L8.464 8.464M9.878 9.878l-6.415-6.414M14.12 14.12l4.242 4.242M14.12 14.12L15.536 15.536M14.12 14.12l6.415 6.415M3 3l6 6m6 6l6 6" />
                </svg>
                <svg v-else class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                </svg>
              </button>
            </div>
          </div>
          
          <button 
            :disabled="authStore.loading"
            class="btn btn-primary w-full" 
            type="submit"
          >
            <svg v-if="authStore.loading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            {{ authStore.loading ? 'Signing In...' : 'Sign In' }}
          </button>
          
          <!-- Sample Credentials -->
          <div class="mt-6 p-4 bg-gradient-to-r from-macaroon-vanilla-50 to-macaroon-vanilla-100 dark:from-gray-700 dark:to-gray-600 rounded-xl border border-macaroon-vanilla-200 dark:border-gray-600">
            <div class="flex items-center mb-2">
              <svg class="w-4 h-4 text-macaroon-vanilla-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <p class="text-sm font-medium text-macaroon-vanilla-800 dark:text-macaroon-vanilla-200">Sample credentials:</p>
            </div>
            <p class="text-xs text-macaroon-vanilla-700 dark:text-macaroon-vanilla-300 mb-1">Email: tom@example.com</p>
            <p class="text-xs text-macaroon-vanilla-700 dark:text-macaroon-vanilla-300">Password: password123</p>
          </div>
        </div>
      </form>
      
      <div class="text-center mt-6">
        <p class="text-xs text-gray-500 dark:text-gray-400">
          Don't have an account? 
          <router-link to="/signup" class="text-macaroon-peach-600 hover:text-macaroon-peach-700 font-medium">
            Sign up here
          </router-link>
        </p>
      </div>        <p class="text-center text-xs text-gray-400 dark:text-gray-500 mt-8">
          &copy;2025 Sparklet Corp. All rights reserved.
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const form = ref({
  email: '',
  password: ''
})

const showPassword = ref(false)

onMounted(() => {
  // Clear any previous errors
  authStore.clearError()
  
  // If already logged in, redirect to dashboard
  if (authStore.isAuthenticated) {
    router.push('/dashboard')
  }
})

async function handleLogin() {
  const result = await authStore.login(form.value)
  
  if (result.success) {
    router.push('/dashboard')
  }
}
</script>
