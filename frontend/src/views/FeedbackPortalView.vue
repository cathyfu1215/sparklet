<template>
  <div class="max-w-4xl mx-auto">
    <!-- Loading State -->
    <div v-if="loading" class="text-center py-8">
      <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-500 mx-auto"></div>
      <p class="mt-4 text-gray-600 dark:text-gray-300">Loading page information...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
      <p>{{ error }}</p>
      <button @click="fetchPageInfo" class="mt-2 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
        Retry
      </button>
    </div>

    <!-- Page Content -->
    <div v-else-if="pageInfo" class="text-center space-y-6">
      <div>
        <h1 class="text-4xl font-bold text-gray-800 dark:text-white">{{ pageInfo.pageName }}</h1>
        <p class="mt-4 text-lg text-gray-600 dark:text-gray-300 max-w-3xl mx-auto">{{ pageInfo.description }}</p>
      </div>

      <!-- Status Badge -->
      <div class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200">
        <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
          <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clip-rule="evenodd"></path>
        </svg>
        {{ pageInfo.status }}
      </div>

      <!-- Coming Soon Info -->
      <div class="bg-indigo-50 dark:bg-indigo-900/20 border border-indigo-200 dark:border-indigo-800 rounded-lg p-6">
        <div class="flex items-start">
          <svg class="w-6 h-6 text-indigo-500 mt-1 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z"></path>
          </svg>
          <div>
            <h3 class="text-lg font-semibold text-indigo-800 dark:text-indigo-200 mb-2">Coming Soon</h3>
            <p class="text-indigo-700 dark:text-indigo-300">{{ pageInfo.comingSoon }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import pageService, { type PageInfo } from '../services/pageService'

const pageInfo = ref<PageInfo | null>(null)
const loading = ref(false)
const error = ref<string | null>(null)

async function fetchPageInfo() {
  loading.value = true
  error.value = null
  
  try {
    const response = await pageService.getPageInfo('feedback-portal')
    if (response.success) {
      pageInfo.value = response.data
    } else {
      error.value = response.message || 'Failed to load page information'
    }
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to load page information'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchPageInfo()
})
</script>
