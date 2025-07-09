<template>
  <div class="section">
    <div class="section-header">
      <h1 class="section-title">Dashboard</h1>
      <p v-if="authStore.currentUser" class="section-subtitle">
        Welcome back, {{ authStore.currentUser.firstName }}! Here's your job search progress.
      </p>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center py-12">
      <div class="spinner h-16 w-16 mx-auto"></div>
      <p class="mt-4 text-gray-600 dark:text-gray-300 font-medium">Loading your dashboard...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="alert alert-error">
      <p>{{ error }}</p>
      <button @click="fetchStats" class="btn btn-primary mt-4">
        Retry
      </button>
    </div>

    <!-- Dashboard Stats -->
    <div v-else-if="stats" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
      <div class="card hover:shadow-macaroon-peach">
        <div class="card-body text-center">
          <div class="w-12 h-12 bg-gradient-to-r from-macaroon-peach-200 to-macaroon-peach-300 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-macaroon-peach-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Total Applications</h3>
          <p class="text-3xl font-bold text-macaroon-peach-600">{{ stats.totalApplications }}</p>
        </div>
      </div>
      
      <div class="card hover:shadow-macaroon-mint">
        <div class="card-body text-center">
          <div class="w-12 h-12 bg-gradient-to-r from-macaroon-mint-200 to-macaroon-mint-300 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-macaroon-mint-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Active Applications</h3>
          <p class="text-3xl font-bold text-macaroon-mint-600">{{ stats.activeApplications }}</p>
        </div>
      </div>
      
      <div class="card hover:shadow-macaroon-lavender">
        <div class="card-body text-center">
          <div class="w-12 h-12 bg-gradient-to-r from-macaroon-lavender-200 to-macaroon-lavender-300 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-macaroon-lavender-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Interviews Scheduled</h3>
          <p class="text-3xl font-bold text-macaroon-lavender-600">{{ stats.interviewsScheduled }}</p>
        </div>
      </div>
      
      <div class="card hover:shadow-macaroon-vanilla">
        <div class="card-body text-center">
          <div class="w-12 h-12 bg-gradient-to-r from-macaroon-vanilla-200 to-macaroon-vanilla-300 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-macaroon-vanilla-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364-.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Practice Problems</h3>
          <p class="text-3xl font-bold text-macaroon-vanilla-700">{{ stats.practiceProblemsCompleted }}</p>
        </div>
      </div>
      
      <div class="card hover:shadow-macaroon-blush">
        <div class="card-body text-center">
          <div class="w-12 h-12 bg-gradient-to-r from-macaroon-blush-200 to-macaroon-blush-300 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-macaroon-blush-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Feedback Received</h3>
          <p class="text-3xl font-bold text-macaroon-blush-600">{{ stats.feedbackReceived }}</p>
        </div>
      </div>
      
      <div class="card hover:shadow-macaroon-peach">
        <div class="card-body text-center">
          <div class="w-12 h-12 bg-gradient-to-r from-macaroon-peach-200 to-macaroon-peach-300 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-macaroon-peach-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
            </svg>
          </div>
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Preparation Score</h3>
          <p class="text-3xl font-bold text-macaroon-peach-600">{{ stats.preparationScore }}%</p>
        </div>
      </div>
    </div>

    <!-- Quick Actions -->
    <div class="card">
      <div class="card-header">
        <h3 class="text-xl font-semibold text-gray-800 dark:text-white">Quick Actions</h3>
      </div>
      <div class="card-body">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <router-link to="/application-tracker" class="group block">
            <div class="bg-gradient-to-r from-macaroon-peach-400 to-macaroon-peach-500 text-white p-6 rounded-xl text-center hover:shadow-macaroon-peach hover:-translate-y-1 transition-all duration-300">
              <svg class="w-8 h-8 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
              </svg>
              <div class="text-sm font-medium">Add New</div>
              <div class="font-semibold">Application</div>
            </div>
          </router-link>
          
          <router-link to="/problem-solving" class="group block">
            <div class="bg-gradient-to-r from-macaroon-mint-400 to-macaroon-mint-500 text-white p-6 rounded-xl text-center hover:shadow-macaroon-mint hover:-translate-y-1 transition-all duration-300">
              <svg class="w-8 h-8 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364-.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
              <div class="text-sm font-medium">Practice</div>
              <div class="font-semibold">Problems</div>
            </div>
          </router-link>
          
          <router-link to="/interview-prep" class="group block">
            <div class="bg-gradient-to-r from-macaroon-lavender-400 to-macaroon-lavender-500 text-white p-6 rounded-xl text-center hover:shadow-macaroon-lavender hover:-translate-y-1 transition-all duration-300">
              <svg class="w-8 h-8 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <div class="text-sm font-medium">Interview</div>
              <div class="font-semibold">Prep</div>
            </div>
          </router-link>
          
          <router-link to="/feedback-portal" class="group block">
            <div class="bg-gradient-to-r from-macaroon-vanilla-400 to-macaroon-vanilla-500 text-white p-6 rounded-xl text-center hover:shadow-macaroon-vanilla hover:-translate-y-1 transition-all duration-300">
              <svg class="w-8 h-8 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
              </svg>
              <div class="text-sm font-medium">Request</div>
              <div class="font-semibold">Feedback</div>
            </div>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import dashboardService from '../services/dashboardService'
import type { DashboardStats } from '../services/dashboardService'

const authStore = useAuthStore()
const loading = ref(false)
const error = ref<string | null>(null)
const stats = ref<DashboardStats | null>(null)

async function fetchStats() {
  loading.value = true
  error.value = null
  
  try {
    const response = await dashboardService.getStats()
    if (response.success) {
      stats.value = response.data
    } else {
      error.value = response.message || 'Failed to load dashboard stats'
    }
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to load dashboard stats'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchStats()
})
</script>
