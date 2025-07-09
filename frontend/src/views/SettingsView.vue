<template>
  <div class="max-w-2xl mx-auto">
    <div class="bg-white dark:bg-gray-800 shadow-md rounded-lg p-6">
      <h1 class="text-3xl font-bold text-gray-800 dark:text-white mb-6">User Settings</h1>
      
      <!-- Loading State -->
      <div v-if="loading" class="text-center py-8">
        <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-500 mx-auto"></div>
        <p class="mt-4 text-gray-600 dark:text-gray-300">Loading profile...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
        <p>{{ error }}</p>
        <button @click="fetchProfile" class="mt-2 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
          Retry
        </button>
      </div>

      <!-- Success Message -->
      <div v-if="successMessage" class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
        <p>{{ successMessage }}</p>
      </div>

      <!-- Profile Form -->
      <form v-if="profile" @submit.prevent="updateProfile" class="space-y-6">
        <!-- Profile Photo -->
        <div class="flex items-center space-x-6">
          <div class="shrink-0">
            <img :src="form.profilePhotoUrl" :alt="`${form.firstName} ${form.lastName}`" 
                 class="h-16 w-16 object-cover rounded-full border-2 border-gray-300 dark:border-gray-600">
          </div>
          <div class="flex-1">
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              Profile Photo URL
            </label>
            <input
              v-model="form.profilePhotoUrl"
              type="url"
              class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              placeholder="https://example.com/photo.jpg"
            >
          </div>
        </div>

        <!-- First Name -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            First Name
          </label>
          <input
            v-model="form.firstName"
            type="text"
            required
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
            placeholder="Enter your first name"
          >
        </div>

        <!-- Last Name -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            Last Name
          </label>
          <input
            v-model="form.lastName"
            type="text"
            required
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
            placeholder="Enter your last name"
          >
        </div>

        <!-- Email (Read-only) -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            Email Address
          </label>
          <input
            :value="profile.email"
            type="email"
            readonly
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm bg-gray-50 dark:bg-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed"
          >
          <p class="mt-1 text-sm text-gray-500 dark:text-gray-400">Email address cannot be changed</p>
        </div>

        <!-- MBTI Type -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            MBTI Type
          </label>
          <select
            v-model="form.mbtiType"
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
          >
            <option value="">Select MBTI Type (Optional)</option>
            <option value="INTJ">INTJ - The Architect</option>
            <option value="INTP">INTP - The Thinker</option>
            <option value="ENTJ">ENTJ - The Commander</option>
            <option value="ENTP">ENTP - The Debater</option>
            <option value="INFJ">INFJ - The Advocate</option>
            <option value="INFP">INFP - The Mediator</option>
            <option value="ENFJ">ENFJ - The Protagonist</option>
            <option value="ENFP">ENFP - The Campaigner</option>
            <option value="ISTJ">ISTJ - The Logistician</option>
            <option value="ISFJ">ISFJ - The Protector</option>
            <option value="ESTJ">ESTJ - The Executive</option>
            <option value="ESFJ">ESFJ - The Consul</option>
            <option value="ISTP">ISTP - The Virtuoso</option>
            <option value="ISFP">ISFP - The Adventurer</option>
            <option value="ESTP">ESTP - The Entrepreneur</option>
            <option value="ESFP">ESFP - The Entertainer</option>
          </select>
        </div>

        <!-- LinkedIn URL -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            LinkedIn Profile
          </label>
          <input
            v-model="form.linkedinUrl"
            type="url"
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
            placeholder="https://linkedin.com/in/your-profile"
          >
        </div>

        <!-- Personal Website -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            Personal Website
          </label>
          <input
            v-model="form.personalWebsiteUrl"
            type="url"
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
            placeholder="https://your-website.com"
          >
        </div>

        <!-- GitHub URL -->
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            GitHub Profile
          </label>
          <input
            v-model="form.githubUrl"
            type="url"
            class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
            placeholder="https://github.com/your-username"
          >
        </div>

        <!-- Account Info -->
        <div class="bg-gray-50 dark:bg-gray-700 p-4 rounded-md">
          <h3 class="text-lg font-medium text-gray-800 dark:text-white mb-2">Account Information</h3>
          <div class="space-y-1 text-sm text-gray-600 dark:text-gray-400">
            <p><strong>Account Created:</strong> {{ formatDate(profile.createdAt) }}</p>
            <p><strong>Last Updated:</strong> {{ formatDate(profile.updatedAt) }}</p>
            <p><strong>User ID:</strong> {{ profile.id }}</p>
            <div class="flex items-center justify-between mt-3">
              <div>
                <p><strong>Account Type:</strong> 
                  <span :class="profile.accountType === 'PREMIUM' ? 'text-yellow-600 font-semibold' : 'text-gray-600'">
                    {{ profile.accountType }}
                  </span>
                </p>
                <p v-if="profile.accountType === 'PREMIUM' && profile.premiumExpiryDate" class="text-xs mt-1">
                  <strong>Premium expires:</strong> {{ formatDate(profile.premiumExpiryDate) }}
                  <span :class="isPremiumExpired ? 'text-red-500' : isExpiringMon ? 'text-yellow-500' : 'text-green-500'">
                    {{ getPremiumStatus() }}
                  </span>
                </p>
              </div>
              <div v-if="profile.accountType === 'BASIC' || isPremiumExpired">
                <button
                  @click="goToPayment"
                  class="px-3 py-1 text-xs bg-yellow-500 text-white rounded hover:bg-yellow-600"
                >
                  Upgrade to Premium
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-end space-x-3">
          <button
            type="button"
            @click="resetForm"
            class="px-4 py-2 border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-50 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Reset
          </button>
          <button
            type="submit"
            :disabled="updating || !hasChanges"
            class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {{ updating ? 'Updating...' : 'Update Profile' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import userService, { type UserProfile, type UpdateUserProfile } from '../services/userService'

const router = useRouter()
const authStore = useAuthStore()

const profile = ref<UserProfile | null>(null)
const loading = ref(false)
const updating = ref(false)
const error = ref<string | null>(null)
const successMessage = ref<string | null>(null)

const form = reactive({
  firstName: '',
  lastName: '',
  profilePhotoUrl: '',
  mbtiType: '',
  linkedinUrl: '',
  personalWebsiteUrl: '',
  githubUrl: ''
})

const originalForm = ref({
  firstName: '',
  lastName: '',
  profilePhotoUrl: '',
  mbtiType: '',
  linkedinUrl: '',
  personalWebsiteUrl: '',
  githubUrl: ''
})

const hasChanges = computed(() => {
  return form.firstName !== originalForm.value.firstName ||
         form.lastName !== originalForm.value.lastName ||
         form.profilePhotoUrl !== originalForm.value.profilePhotoUrl ||
         form.mbtiType !== originalForm.value.mbtiType ||
         form.linkedinUrl !== originalForm.value.linkedinUrl ||
         form.personalWebsiteUrl !== originalForm.value.personalWebsiteUrl ||
         form.githubUrl !== originalForm.value.githubUrl
})

const isPremiumExpired = computed(() => {
  if (!profile.value || profile.value.accountType !== 'PREMIUM' || !profile.value.premiumExpiryDate) {
    return false
  }
  return new Date(profile.value.premiumExpiryDate) < new Date()
})

const isExpiringMon = computed(() => {
  if (!profile.value || profile.value.accountType !== 'PREMIUM' || !profile.value.premiumExpiryDate) {
    return false
  }
  const expiryDate = new Date(profile.value.premiumExpiryDate)
  const now = new Date()
  const diffDays = Math.ceil((expiryDate.getTime() - now.getTime()) / (1000 * 60 * 60 * 24))
  return diffDays <= 7 && diffDays > 0
})

async function fetchProfile() {
  loading.value = true
  error.value = null
  
  try {
    const response = await userService.getProfile()
    if (response.success) {
      profile.value = response.data
      updateForm(response.data)
    } else {
      error.value = response.message || 'Failed to load profile'
    }
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to load profile'
  } finally {
    loading.value = false
  }
}

function updateForm(profileData: UserProfile) {
  form.firstName = profileData.firstName
  form.lastName = profileData.lastName
  form.profilePhotoUrl = profileData.profilePhotoUrl
  form.mbtiType = profileData.mbtiType || ''
  form.linkedinUrl = profileData.linkedinUrl || ''
  form.personalWebsiteUrl = profileData.personalWebsiteUrl || ''
  form.githubUrl = profileData.githubUrl || ''
  
  originalForm.value = {
    firstName: profileData.firstName,
    lastName: profileData.lastName,
    profilePhotoUrl: profileData.profilePhotoUrl,
    mbtiType: profileData.mbtiType || '',
    linkedinUrl: profileData.linkedinUrl || '',
    personalWebsiteUrl: profileData.personalWebsiteUrl || '',
    githubUrl: profileData.githubUrl || ''
  }
}

function resetForm() {
  if (profile.value) {
    updateForm(profile.value)
  }
  successMessage.value = null
  error.value = null
}

async function updateProfile() {
  updating.value = true
  error.value = null
  successMessage.value = null
  
  try {
    const updateData: UpdateUserProfile = {
      firstName: form.firstName,
      lastName: form.lastName,
      profilePhotoUrl: form.profilePhotoUrl,
      mbtiType: form.mbtiType || undefined,
      linkedinUrl: form.linkedinUrl || undefined,
      personalWebsiteUrl: form.personalWebsiteUrl || undefined,
      githubUrl: form.githubUrl || undefined
    }
    
    const response = await userService.updateProfile(updateData)
    if (response.success) {
      profile.value = response.data
      updateForm(response.data)
      
      // Update auth store with new user data
      authStore.user = {
        ...authStore.user,
        firstName: response.data.firstName,
        lastName: response.data.lastName,
        profilePhotoUrl: response.data.profilePhotoUrl,
        mbtiType: response.data.mbtiType,
        linkedinUrl: response.data.linkedinUrl,
        personalWebsiteUrl: response.data.personalWebsiteUrl,
        githubUrl: response.data.githubUrl
      }
      
      // Update localStorage
      localStorage.setItem('user', JSON.stringify(authStore.user))
      
      successMessage.value = 'Profile updated successfully!'
      
      // Clear success message after 3 seconds
      setTimeout(() => {
        successMessage.value = null
      }, 3000)
    } else {
      error.value = response.message || 'Failed to update profile'
    }
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to update profile'
  } finally {
    updating.value = false
  }
}

function formatDate(dateString: string) {
  return new Date(dateString).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function getPremiumStatus() {
  if (isPremiumExpired.value) {
    return ' (Expired)'
  }
  if (isExpiringMon.value) {
    return ' (Expiring Soon)'
  }
  return ' (Active)'
}

function goToPayment() {
  router.push('/payment')
}

onMounted(() => {
  fetchProfile()
})
</script>
