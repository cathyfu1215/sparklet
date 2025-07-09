<template>
  <div class="min-h-screen bg-gradient-to-br from-macaroon-vanilla-50 via-white to-macaroon-peach-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900">
    <!-- Navigation -->
    <nav class="bg-white/90 dark:bg-gray-800/90 backdrop-blur-md shadow-soft sticky top-0 z-50 border-b border-macaroon-peach-100 dark:border-gray-700">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between items-center h-16">
          <!-- Logo -->
          <div class="flex items-center">
            <h1 class="text-2xl font-bold bg-gradient-to-r from-macaroon-peach-400 to-macaroon-lavender-400 bg-clip-text text-transparent">
              Sparklet
            </h1>
          </div>

          <!-- Navigation Links -->
          <div class="flex items-center space-x-4">
            <!-- Dark Mode Toggle -->
            <button 
              @click="$emit('toggle-dark-mode')" 
              class="p-2 rounded-xl text-gray-500 hover:text-macaroon-peach-600 hover:bg-macaroon-peach-50 dark:text-gray-300 dark:hover:text-white dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-macaroon-peach-200 transition-all duration-200" 
              aria-label="Toggle dark mode"
            >
              <svg v-if="isDarkMode" class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
              </svg>
              <svg v-else class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
              </svg>
            </button>

            <button 
              v-if="isAuthenticated"
              @click="handleLogout"
              class="nav-link"
            >
              Logout
            </button>
            <router-link 
              v-else
              to="/login" 
              class="nav-link"
              @click="handleLoginClick"
            >
              Login
            </router-link>
            <router-link 
              v-if="!isAuthenticated"
              to="/signup" 
              class="btn btn-primary"
              @click="handleSignupClick"
            >
              Sign Up
            </router-link>
            <router-link 
              v-else
              to="/dashboard" 
              class="btn btn-primary"
            >
              Dashboard
            </router-link>
          </div>
        </div>
      </div>
    </nav>

    <!-- Hero Section -->
    <section class="relative py-24 px-4" ref="heroSection">
      <div class="max-w-7xl mx-auto text-center">
        <div class="max-w-4xl mx-auto">
          <h2 class="text-5xl md:text-6xl font-bold mb-6 leading-tight">
            <span class="bg-gradient-to-r from-macaroon-peach-500 to-macaroon-lavender-500 bg-clip-text text-transparent">
              Elevate Your
            </span>
            <br>
            <span class="text-gray-900 dark:text-gray-100">Career Journey</span>
          </h2>
          
          <p class="text-xl text-gray-600 dark:text-gray-300 mb-8 leading-relaxed max-w-2xl mx-auto">
            Sparklet helps you track job applications, prepare for interviews, and get valuable feedback to land your dream career.
          </p>
          
          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <router-link 
              v-if="!isAuthenticated"
              to="/signup" 
              class="btn btn-primary text-lg px-8 py-4"
              @click="handleSignupClick"
            >
              Get Started Free
            </router-link>
            <router-link 
              v-else
              to="/dashboard" 
              class="btn btn-primary text-lg px-8 py-4"
            >
              Go to Dashboard
            </router-link>
            <button 
              @click="scrollToFeatures"
              class="btn btn-outline text-lg px-8 py-4"
            >
              Learn More
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="section bg-white/60 dark:bg-gray-800/60" ref="featuresSection">
      <div class="max-w-7xl mx-auto px-4">
        <div class="section-header">
          <h2 class="section-title">
            Everything You Need to <span class="bg-gradient-to-r from-macaroon-mint-500 to-macaroon-peach-500 bg-clip-text text-transparent">Succeed</span>
          </h2>
          <p class="section-subtitle">
            Comprehensive tools designed to help you land your dream job
          </p>
        </div>

        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- Job Application Tracker -->
          <div class="card hover:scale-105 transition-transform duration-300">
            <div class="card-body">
              <div class="w-12 h-12 bg-gradient-to-br from-macaroon-peach-100 to-macaroon-peach-200 rounded-xl flex items-center justify-center mb-4">
                <svg class="w-6 h-6 text-macaroon-peach-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01" />
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">Job Application Tracker</h3>
              <p class="text-gray-600 dark:text-gray-300">
                Keep track of all your job applications, interviews, and follow-ups in one organized dashboard.
              </p>
            </div>
          </div>

          <!-- Problem Solving Practice -->
          <div class="card hover:scale-105 transition-transform duration-300">
            <div class="card-body">
              <div class="w-12 h-12 bg-gradient-to-br from-macaroon-mint-100 to-macaroon-mint-200 rounded-xl flex items-center justify-center mb-4">
                <svg class="w-6 h-6 text-macaroon-mint-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">Problem Solving Practice</h3>
              <p class="text-gray-600 dark:text-gray-300">
                Sharpen your coding and analytical skills with curated problems and solutions.
              </p>
            </div>
          </div>

          <!-- Interview Preparation -->
          <div class="card hover:scale-105 transition-transform duration-300">
            <div class="card-body">
              <div class="w-12 h-12 bg-gradient-to-br from-macaroon-lavender-100 to-macaroon-lavender-200 rounded-xl flex items-center justify-center mb-4">
                <svg class="w-6 h-6 text-macaroon-lavender-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">Interview Preparation</h3>
              <p class="text-gray-600 dark:text-gray-300">
                Practice common interview questions and get tips to ace your next interview.
              </p>
            </div>
          </div>

          <!-- Performance Dashboard -->
          <div class="card hover:scale-105 transition-transform duration-300">
            <div class="card-body">
              <div class="w-12 h-12 bg-gradient-to-br from-macaroon-vanilla-100 to-macaroon-vanilla-200 rounded-xl flex items-center justify-center mb-4">
                <svg class="w-6 h-6 text-macaroon-vanilla-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">Performance Dashboard</h3>
              <p class="text-gray-600 dark:text-gray-300">
                Track your progress and get insights into your job search performance.
              </p>
            </div>
          </div>

          <!-- Feedback Portal -->
          <div class="card hover:scale-105 transition-transform duration-300">
            <div class="card-body">
              <div class="w-12 h-12 bg-gradient-to-br from-macaroon-blush-100 to-macaroon-blush-200 rounded-xl flex items-center justify-center mb-4">
                <svg class="w-6 h-6 text-macaroon-blush-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">Feedback Portal</h3>
              <p class="text-gray-600 dark:text-gray-300">
                Get constructive feedback on your applications and interview performance.
              </p>
            </div>
          </div>

          <!-- Career Management -->
          <div class="card hover:scale-105 transition-transform duration-300">
            <div class="card-body">
              <div class="w-12 h-12 bg-gradient-to-br from-macaroon-peach-200 to-macaroon-lavender-200 rounded-xl flex items-center justify-center mb-4">
                <svg class="w-6 h-6 text-macaroon-peach-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                </svg>
              </div>
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">Career Management</h3>
              <p class="text-gray-600 dark:text-gray-300">
                Manage your profile, settings, and career goals all in one place.
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="section bg-gradient-to-r from-macaroon-peach-400 via-macaroon-lavender-400 to-macaroon-mint-400">
      <div class="max-w-4xl mx-auto px-4 text-center">
        <h2 class="text-3xl md:text-4xl font-bold text-white mb-4">
          Ready to Transform Your Career?
        </h2>
        <p class="text-xl text-white/90 mb-8">
          Join thousands of professionals who have already started their journey with Sparklet
        </p>
        <router-link 
          to="/signup" 
          class="btn bg-white text-macaroon-peach-600 hover:bg-gray-50 px-8 py-4 text-lg font-semibold shadow-lg"
        >
          Get Started Free
        </router-link>
      </div>
    </section>

    <!-- Footer -->
    <footer class="bg-gray-900 text-white py-12 px-4">
      <div class="max-w-7xl mx-auto text-center">
        <div class="mb-8">
          <h3 class="text-2xl font-bold bg-gradient-to-r from-macaroon-peach-400 to-macaroon-lavender-400 bg-clip-text text-transparent mb-2">Sparklet</h3>
          <p class="text-gray-400">Accelerating careers, one application at a time</p>
        </div>
        <div class="border-t border-gray-800 pt-8">
          <p class="text-gray-400">&copy; 2025 Sparklet Corp. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
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
const featuresSection = ref<HTMLElement>()

const isAuthenticated = computed(() => authStore.isAuthenticated)

function scrollToFeatures() {
  featuresSection.value?.scrollIntoView({ behavior: 'smooth' })
}

function handleLogout() {
  authStore.forceLogout()
  router.push('/')
}

function handleLoginClick() {
  // Force clear any stale auth data before navigating to login
  if (authStore.isAuthenticated) {
    authStore.forceLogout()
  }
  router.push('/login?force=true')
}

function handleSignupClick() {
  // Force clear any stale auth data before navigating to signup
  if (authStore.isAuthenticated) {
    authStore.forceLogout()
  }
  router.push('/signup?force=true')
}
</script>
