<template>
  <div class="space-y-6">
    <div class="text-center">
      <h1 class="text-4xl font-bold text-gray-800 dark:text-white">Dashboard</h1>
      <p v-if="authStore.currentUser" class="mt-2 text-lg text-gray-600 dark:text-gray-300">
        Welcome back, {{ authStore.currentUser.firstName }}!
      </p>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center">
      <div class="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-500 mx-auto"></div>
      <p class="mt-4 text-gray-600 dark:text-gray-300">Loading your dashboard...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
      <p>{{ error }}</p>
      <button @click="fetchStats" class="mt-2 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
        Retry
      </button>
    </div>

    <!-- Dashboard Stats -->
    <div v-else-if="stats" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Total Applications</h3>
        <p class="text-3xl font-bold text-blue-600">{{ stats.totalApplications }}</p>
      </div>
      
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Active Applications</h3>
        <p class="text-3xl font-bold text-green-600">{{ stats.activeApplications }}</p>
      </div>
      
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Interviews Scheduled</h3>
        <p class="text-3xl font-bold text-purple-600">{{ stats.interviewsScheduled }}</p>
      </div>
      
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Practice Problems</h3>
        <p class="text-3xl font-bold text-yellow-600">{{ stats.practiceProblemsCompleted }}</p>
      </div>
      
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Feedback Received</h3>
        <p class="text-3xl font-bold text-indigo-600">{{ stats.feedbackReceived }}</p>
      </div>
      
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Preparation Score</h3>
        <p class="text-3xl font-bold text-red-600">{{ stats.preparationScore }}%</p>
      </div>
    </div>

    <!-- Quick Actions -->
    <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
      <h3 class="text-xl font-semibold text-gray-800 dark:text-white mb-4">Quick Actions</h3>
      <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <router-link to="/application-tracker" class="bg-blue-500 text-white p-4 rounded-lg text-center hover:bg-blue-600 transition-colors">
          <div class="text-sm">Add New</div>
          <div class="font-semibold">Application</div>
        </router-link>
        
        <router-link to="/problem-solving" class="bg-green-500 text-white p-4 rounded-lg text-center hover:bg-green-600 transition-colors">
          <div class="text-sm">Practice</div>
          <div class="font-semibold">Problems</div>
        </router-link>
        
        <router-link to="/interview-prep" class="bg-purple-500 text-white p-4 rounded-lg text-center hover:bg-purple-600 transition-colors">
          <div class="text-sm">Interview</div>
          <div class="font-semibold">Prep</div>
        </router-link>
        
        <router-link to="/feedback-portal" class="bg-orange-500 text-white p-4 rounded-lg text-center hover:bg-orange-600 transition-colors">
          <div class="text-sm">Request</div>
          <div class="font-semibold">Feedback</div>
        </router-link>
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
