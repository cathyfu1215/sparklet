<template>
  <div class="flex items-center justify-center min-h-screen">
    <div class="w-full max-w-md">
      <form @submit.prevent="handleLogin" class="bg-white dark:bg-gray-800 shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <h2 class="text-2xl font-bold text-center mb-6 text-gray-900 dark:text-white">Sign In to Sparklet</h2>
        
        <!-- Error Message -->
        <div v-if="authStore.error" class="mb-4 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
          {{ authStore.error }}
        </div>
        
        <div class="mb-4">
          <label class="block text-gray-700 dark:text-gray-300 text-sm font-bold mb-2" for="email">
            Email
          </label>
          <input 
            v-model="form.email"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 dark:text-gray-300 leading-tight focus:outline-none focus:shadow-outline" 
            id="email" 
            type="email" 
            placeholder="your@email.com"
            required
          >
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 dark:text-gray-300 text-sm font-bold mb-2" for="password">
            Password
          </label>
          <div class="relative">
            <input 
              v-model="form.password"
              class="shadow appearance-none border rounded w-full py-2 px-3 pr-10 text-gray-700 dark:text-gray-300 mb-3 leading-tight focus:outline-none focus:shadow-outline" 
              id="password" 
              :type="showPassword ? 'text' : 'password'" 
              placeholder="******************"
              required
            >
            <button 
              type="button"
              @click="showPassword = !showPassword"
              class="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-600 dark:text-gray-500 dark:hover:text-gray-300"
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
        <div class="flex items-center justify-between">
          <button 
            :disabled="authStore.loading"
            class="bg-blue-500 hover:bg-blue-700 disabled:bg-blue-300 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full" 
            type="submit"
          >
            {{ authStore.loading ? 'Signing In...' : 'Sign In' }}
          </button>
        </div>
        
        <!-- Sample Credentials -->
        <div class="mt-4 p-3 bg-gray-100 dark:bg-gray-700 rounded">
          <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">Sample credentials:</p>
          <p class="text-xs text-gray-500 dark:text-gray-500">Email: tom@example.com</p>
          <p class="text-xs text-gray-500 dark:text-gray-500">Password: password123</p>
        </div>
      </form>
      <p class="text-center text-gray-500 text-xs">
        &copy;2025 Sparklet Corp. All rights reserved.
      </p>
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
    router.push('/')
  }
})

async function handleLogin() {
  const result = await authStore.login(form.value)
  
  if (result.success) {
    router.push('/')
  }
}
</script>
