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
          <input 
            v-model="form.password"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 dark:text-gray-300 mb-3 leading-tight focus:outline-none focus:shadow-outline" 
            id="password" 
            type="password" 
            placeholder="******************"
            required
          >
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
