<template>
  <nav class="bg-white dark:bg-gray-800 shadow-lg">
    <div class="max-w-7xl mx-auto px-4">
      <div class="flex justify-between items-center h-16">
        <!-- Logo -->
        <div class="flex items-center">
          <router-link to="/" class="text-xl font-bold text-blue-600 dark:text-blue-400">
            Sparklet
          </router-link>
        </div>

        <!-- Navigation Links -->
        <div v-if="authStore.isAuthenticated" class="hidden md:flex space-x-8">
          <router-link 
            to="/" 
            class="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 px-3 py-2 rounded-md text-sm font-medium"
            :class="{ 'text-blue-600 dark:text-blue-400 font-semibold': $route.name === 'dashboard' }"
          >
            Dashboard
          </router-link>
          
          <router-link 
            to="/application-tracker" 
            class="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 px-3 py-2 rounded-md text-sm font-medium"
            :class="{ 'text-blue-600 dark:text-blue-400 font-semibold': $route.name === 'application-tracker' }"
          >
            Job Tracker
          </router-link>
          
          <router-link 
            to="/problem-solving" 
            class="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 px-3 py-2 rounded-md text-sm font-medium"
            :class="{ 'text-blue-600 dark:text-blue-400 font-semibold': $route.name === 'problem-solving' }"
          >
            Practice
          </router-link>
          
          <router-link 
            to="/interview-prep" 
            class="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 px-3 py-2 rounded-md text-sm font-medium"
            :class="{ 'text-blue-600 dark:text-blue-400 font-semibold': $route.name === 'interview-prep' }"
          >
            Interview Prep
          </router-link>
          
          <router-link 
            to="/feedback-portal" 
            class="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 px-3 py-2 rounded-md text-sm font-medium"
            :class="{ 'text-blue-600 dark:text-blue-400 font-semibold': $route.name === 'feedback-portal' }"
          >
            Feedback
          </router-link>
        </div>

        <!-- User Menu -->
        <div v-if="authStore.isAuthenticated" class="flex items-center space-x-4">
          <span class="text-gray-700 dark:text-gray-300 text-sm">
            {{ authStore.currentUser?.firstName }} {{ authStore.currentUser?.lastName }}
          </span>
          
          <div class="relative">
            <button 
              @click="showUserMenu = !showUserMenu"
              class="flex items-center text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
              </svg>
            </button>
            
            <div v-if="showUserMenu" class="absolute right-0 mt-2 w-48 bg-white dark:bg-gray-800 rounded-md shadow-lg py-1 z-10">
              <router-link 
                to="/settings" 
                class="block px-4 py-2 text-sm text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700"
                @click="showUserMenu = false"
              >
                Settings
              </router-link>
              <button 
                @click="handleLogout"
                class="block w-full text-left px-4 py-2 text-sm text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700"
              >
                Logout
              </button>
            </div>
          </div>
        </div>

        <!-- Login Link -->
        <div v-else>
          <router-link 
            to="/login" 
            class="bg-blue-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-blue-700"
          >
            Login
          </router-link>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const showUserMenu = ref(false)

function handleLogout() {
  authStore.logout()
  showUserMenu.value = false
  router.push('/login')
}
</script>
