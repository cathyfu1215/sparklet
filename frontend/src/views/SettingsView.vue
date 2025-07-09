<template>
  <div class="section">
    <div class="max-w-4xl mx-auto">
      <div class="section-header">
        <h1 class="section-title">User Settings</h1>
        <p class="section-subtitle">
          Manage your profile information and account preferences
        </p>
      </div>
      
      <!-- Loading State -->
      <div v-if="loading" class="text-center py-12">
        <div class="spinner h-16 w-16 mx-auto"></div>
        <p class="mt-4 text-gray-600 dark:text-gray-300 font-medium">Loading your profile...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="alert alert-error mb-6">
        <p>{{ error }}</p>
        <button @click="fetchProfile" class="btn btn-primary mt-4">
          Retry
        </button>
      </div>

      <!-- Success Message -->
      <div v-if="successMessage" class="alert alert-success mb-6">
        <p>{{ successMessage }}</p>
      </div>

      <!-- Profile Form -->
      <form v-if="profile" @submit.prevent="updateProfile" class="space-y-8">
        <!-- Profile Information Card -->
        <div class="card">
          <div class="card-header">
            <h3 class="text-xl font-semibold text-gray-800 dark:text-white">Profile Information</h3>
          </div>
          <div class="card-body space-y-6">
            <!-- Profile Photo -->
            <div class="flex items-center space-x-6">
              <div class="shrink-0">
                <img :src="form.profilePhotoUrl" :alt="`${form.firstName} ${form.lastName}`" 
                     class="h-20 w-20 object-cover rounded-full border-4 border-macaroon-peach-200 dark:border-macaroon-peach-600">
              </div>
              <div class="flex-1">
                <label class="form-label">
                  Profile Photo URL
                </label>
                <input
                  v-model="form.profilePhotoUrl"
                  type="url"
                  class="form-control"
                  placeholder="https://example.com/photo.jpg"
                >
              </div>
            </div>

            <!-- Name Fields -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div class="form-group">
                <label class="form-label">
                  First Name
                </label>
                <input
                  v-model="form.firstName"
                  type="text"
                  required
                  class="form-control"
                  placeholder="Enter your first name"
                >
              </div>

              <div class="form-group">
                <label class="form-label">
                  Last Name
                </label>
                <input
                  v-model="form.lastName"
                  type="text"
                  required
                  class="form-control"
                  placeholder="Enter your last name"
                >
              </div>
            </div>

            <!-- Email (Read-only) -->
            <div class="form-group">
              <label class="form-label">
                Email Address
              </label>
              <input
                :value="profile.email"
                type="email"
                readonly
                class="form-control bg-gray-50 dark:bg-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed"
              >
              <p class="mt-2 text-sm text-gray-500 dark:text-gray-400">Email address cannot be changed</p>
            </div>

            <!-- MBTI Type -->
            <div class="form-group">
              <label class="form-label">
                MBTI Type
              </label>
              <select
                v-model="form.mbtiType"
                class="form-select"
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
          </div>
        </div>

        <!-- Social Links Card -->
        <div class="card">
          <div class="card-header">
            <h3 class="text-xl font-semibold text-gray-800 dark:text-white">Social Links</h3>
          </div>
          <div class="card-body space-y-6">
            <!-- LinkedIn URL -->
            <div class="form-group">
              <label class="form-label">
                <svg class="w-4 h-4 inline mr-2" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"/>
                </svg>
                LinkedIn Profile
              </label>
              <input
                v-model="form.linkedinUrl"
                type="url"
                class="form-control"
                placeholder="https://linkedin.com/in/your-profile"
              >
            </div>

            <!-- Personal Website -->
            <div class="form-group">
              <label class="form-label">
                <svg class="w-4 h-4 inline mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 12a9 9 0 01-9 9m9-9a9 9 0 00-9-9m9 9H3m9 9a9 9 0 01-9-9m9 9c1.657 0 3-4.03 3-9s-1.343-9-3-9m0 18c-1.657 0-3-4.03-3-9s1.343-9 3-9m-9 9a9 9 0 019-9" />
                </svg>
                Personal Website
              </label>
              <input
                v-model="form.personalWebsiteUrl"
                type="url"
                class="form-control"
                placeholder="https://your-website.com"
              >
            </div>

            <!-- GitHub URL -->
            <div class="form-group">
              <label class="form-label">
                <svg class="w-4 h-4 inline mr-2" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
                </svg>
                GitHub Profile
              </label>
              <input
                v-model="form.githubUrl"
                type="url"
                class="form-control"
                placeholder="https://github.com/your-username"
              >
            </div>
          </div>
        </div>

        <!-- Account Information Card -->
        <div class="card">
          <div class="card-header">
            <h3 class="text-xl font-semibold text-gray-800 dark:text-white">Account Information</h3>
          </div>
          <div class="card-body">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <p class="text-sm text-gray-600 dark:text-gray-400 mb-1">Account Created</p>
                <p class="font-medium text-gray-900 dark:text-gray-100">{{ formatDate(profile.createdAt) }}</p>
              </div>
              <div>
                <p class="text-sm text-gray-600 dark:text-gray-400 mb-1">Last Updated</p>
                <p class="font-medium text-gray-900 dark:text-gray-100">{{ formatDate(profile.updatedAt) }}</p>
              </div>
              <div>
                <p class="text-sm text-gray-600 dark:text-gray-400 mb-1">User ID</p>
                <p class="font-mono text-sm text-gray-900 dark:text-gray-100">{{ profile.id }}</p>
              </div>
              <div>
                <p class="text-sm text-gray-600 dark:text-gray-400 mb-1">Account Type</p>
                <div class="flex items-center justify-between">
                  <span v-if="profile.accountType === 'PREMIUM'" class="badge badge-premium">
                    {{ profile.accountType }}
                  </span>
                  <span v-else class="badge badge-primary">
                    {{ profile.accountType }}
                  </span>
                  <button
                    v-if="profile.accountType === 'BASIC' || isPremiumExpired"
                    @click="goToPayment"
                    class="btn btn-secondary text-xs py-1 px-3"
                  >
                    Upgrade to Premium
                  </button>
                </div>
                <p v-if="profile.accountType === 'PREMIUM' && profile.premiumExpiryDate" class="text-xs mt-2">
                  <span class="text-gray-600 dark:text-gray-400">Premium expires:</span> 
                  <span class="font-medium">{{ formatDate(profile.premiumExpiryDate) }}</span>
                  <span :class="isPremiumExpired ? 'text-macaroon-blush-600' : isExpiringMon ? 'text-macaroon-vanilla-700' : 'text-macaroon-mint-600'" class="ml-2 font-medium">
                    {{ getPremiumStatus() }}
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-end space-x-4">
          <button
            type="button"
            @click="resetForm"
            class="btn btn-outline"
          >
            Reset Changes
          </button>
          <button
            type="submit"
            :disabled="updating || !hasChanges"
            class="btn btn-primary"
          >
            <svg v-if="updating" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            {{ updating ? 'Updating Profile...' : 'Update Profile' }}
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
