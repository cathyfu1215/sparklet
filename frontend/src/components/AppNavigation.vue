<template>
  <nav class="bg-white dark:bg-gray-800 shadow-soft border-b border-macaroon-peach-100 dark:border-gray-700">
    <div class="max-w-7xl mx-auto px-4">
      <div class="flex justify-between items-center h-16">
        <!-- Logo -->
        <div class="flex items-center">
          <router-link to="/" class="text-xl font-bold bg-gradient-to-r from-macaroon-peach-500 to-macaroon-lavender-500 bg-clip-text text-transparent hover:from-macaroon-peach-600 hover:to-macaroon-lavender-600 transition-all duration-200">
            Sparklet
          </router-link>
        </div>

        <!-- Navigation Links -->
        <div v-if="authStore.isAuthenticated" class="hidden md:flex space-x-2">
          <router-link 
            to="/dashboard" 
            class="nav-link"
            :class="{ 'active': $route.name === 'dashboard' }"
          >
            Dashboard
          </router-link>
          
          <router-link 
            to="/application-tracker" 
            class="nav-link"
            :class="{ 'active': $route.name === 'application-tracker' }"
          >
            Job Tracker
          </router-link>
          
          <router-link 
            to="/problem-solving" 
            class="nav-link"
            :class="{ 'active': $route.name === 'problem-solving' }"
          >
            Problem Solving
          </router-link>
          
          <router-link 
            to="/interview-prep" 
            class="nav-link"
            :class="{ 'active': $route.name === 'interview-prep' }"
          >
            Interview Prep
          </router-link>
          
          <router-link 
            to="/feedback-portal" 
            class="nav-link"
            :class="{ 'active': $route.name === 'feedback-portal' }"
          >
            Feedback
          </router-link>
        </div>

        <!-- User Menu -->
        <div v-if="authStore.isAuthenticated" class="hidden md:flex items-center space-x-4">
          <div class="flex items-center space-x-2">
            <div class="w-8 h-8 rounded-full bg-gradient-to-r from-macaroon-peach-300 to-macaroon-lavender-300 flex items-center justify-center">
              <span class="text-white font-semibold text-sm">
                {{ authStore.currentUser?.firstName?.[0] }}{{ authStore.currentUser?.lastName?.[0] }}
              </span>
            </div>
            <span class="text-gray-700 dark:text-gray-300 text-sm font-medium">
              {{ authStore.currentUser?.firstName }} {{ authStore.currentUser?.lastName }}
            </span>
          </div>

          <!-- Dark Mode Toggle -->
          <button 
            @click="$emit('toggle-dark-mode')" 
            class="p-2 rounded-full text-gray-500 hover:text-macaroon-peach-600 dark:text-gray-300 dark:hover:text-macaroon-peach-400 hover:bg-macaroon-peach-50 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-macaroon-peach-200 transition-all duration-200" 
            aria-label="Toggle dark mode"
          >
            <svg v-if="isDarkMode" class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
            </svg>
            <svg v-else class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
            </svg>
          </button>
          
          <div class="relative">
            <button 
              @click="showUserMenu = !showUserMenu"
              class="flex items-center text-gray-700 dark:text-gray-300 hover:text-macaroon-peach-600 dark:hover:text-macaroon-peach-400 p-2 rounded-lg hover:bg-macaroon-peach-50 dark:hover:bg-gray-700 transition-all duration-200"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
              </svg>
            </button>
            
            <div v-if="showUserMenu" class="absolute right-0 mt-2 w-48 bg-white dark:bg-gray-800 rounded-xl shadow-medium border border-macaroon-peach-100 dark:border-gray-700 py-1 z-10">
              <router-link 
                to="/settings" 
                class="block px-4 py-2 text-sm text-gray-700 dark:text-gray-300 hover:bg-macaroon-peach-50 dark:hover:bg-gray-700 hover:text-macaroon-peach-600 dark:hover:text-macaroon-peach-400 transition-colors duration-200"
                @click="showUserMenu = false"
              >
                Settings
              </router-link>
              <button 
                @click="handleLogout"
                class="block w-full text-left px-4 py-2 text-sm text-gray-700 dark:text-gray-300 hover:bg-macaroon-peach-50 dark:hover:bg-gray-700 hover:text-macaroon-peach-600 dark:hover:text-macaroon-peach-400 transition-colors duration-200"
              >
                Logout
              </button>
            </div>
          </div>
        </div>

        <!-- Mobile Menu and Dark Mode Toggle for authenticated users -->
        <div v-if="authStore.isAuthenticated" class="md:hidden flex items-center space-x-2">
          <!-- Dark Mode Toggle -->
          <button 
            @click="$emit('toggle-dark-mode')" 
            class="p-2 rounded-full text-gray-500 hover:text-gray-900 dark:text-gray-300 dark:hover:text-white focus:outline-none transition-colors" 
            aria-label="Toggle dark mode"
          >
            <svg v-if="isDarkMode" class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
            </svg>
            <svg v-else class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
            </svg>
          </button>

          <!-- Mobile Menu Button -->
          <button 
            @click="showMobileMenu = !showMobileMenu"
            class="p-2 rounded-md text-gray-500 hover:text-gray-900 dark:text-gray-300 dark:hover:text-white focus:outline-none"
            aria-label="Toggle mobile menu"
          >
            <svg v-if="!showMobileMenu" class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
            </svg>
            <svg v-else class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>

        <!-- Login Link -->
        <div v-else class="flex items-center space-x-4">
          <!-- Dark Mode Toggle -->
          <button 
            @click="$emit('toggle-dark-mode')" 
            class="p-2 rounded-full text-gray-500 hover:text-macaroon-peach-600 dark:text-gray-300 dark:hover:text-macaroon-peach-400 hover:bg-macaroon-peach-50 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-macaroon-peach-200 transition-all duration-200" 
            aria-label="Toggle dark mode"
          >
            <svg v-if="isDarkMode" class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
            </svg>
            <svg v-else class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
            </svg>
          </button>

          <router-link 
            to="/login" 
            class="btn btn-primary"
          >
            Login
          </router-link>
        </div>
      </div>

      <!-- Mobile Menu -->
      <div v-if="showMobileMenu && authStore.isAuthenticated" class="md:hidden">
        <div class="px-2 pt-2 pb-3 space-y-1 bg-white dark:bg-gray-800 border-t border-macaroon-peach-100 dark:border-gray-700">
          <router-link 
            to="/dashboard" 
            @click="showMobileMenu = false"
            class="block px-3 py-2 rounded-lg text-base font-medium nav-link"
            :class="{ 'active': $route.name === 'dashboard' }"
          >
            Dashboard
          </router-link>
          
          <router-link 
            to="/application-tracker" 
            @click="showMobileMenu = false"
            class="block px-3 py-2 rounded-lg text-base font-medium nav-link"
            :class="{ 'active': $route.name === 'application-tracker' }"
          >
            Job Tracker
          </router-link>
          
          <router-link 
            to="/problem-solving" 
            @click="showMobileMenu = false"
            class="block px-3 py-2 rounded-lg text-base font-medium nav-link"
            :class="{ 'active': $route.name === 'problem-solving' }"
          >
            Problem Solving
          </router-link>
          
          <router-link 
            to="/interview-prep" 
            @click="showMobileMenu = false"
            class="block px-3 py-2 rounded-lg text-base font-medium nav-link"
            :class="{ 'active': $route.name === 'interview-prep' }"
          >
            Interview Prep
          </router-link>
          
          <router-link 
            to="/feedback-portal" 
            @click="showMobileMenu = false"
            class="block px-3 py-2 rounded-lg text-base font-medium nav-link"
            :class="{ 'active': $route.name === 'feedback-portal' }"
          >
            Feedback
          </router-link>

          <!-- User Info and Actions in Mobile Menu -->
          <div class="pt-4 border-t border-macaroon-peach-100 dark:border-gray-700">
            <div class="px-3 py-2">
              <div class="text-base font-medium text-gray-800 dark:text-white">
                {{ authStore.currentUser?.firstName }} {{ authStore.currentUser?.lastName }}
              </div>
              <div class="text-sm text-gray-500 dark:text-gray-400">
                {{ authStore.currentUser?.email }}
              </div>
            </div>
            <router-link 
              to="/settings" 
              @click="showMobileMenu = false"
              class="block px-3 py-2 rounded-lg text-base font-medium nav-link"
            >
              Settings
            </router-link>
            <button 
              @click="handleLogout"
              class="block w-full text-left px-3 py-2 rounded-lg text-base font-medium nav-link"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

interface Props {
  isDarkMode: boolean
}

defineProps<Props>()
defineEmits<{
  'toggle-dark-mode': []
}>()

const router = useRouter()
const authStore = useAuthStore()
const showUserMenu = ref(false)
const showMobileMenu = ref(false)

function handleLogout() {
  authStore.logout()
  showUserMenu.value = false
  showMobileMenu.value = false
  router.push('/login')
}
</script>
