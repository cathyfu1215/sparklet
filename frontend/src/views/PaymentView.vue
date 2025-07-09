<template>
  <div class="section">
    <div class="max-w-4xl mx-auto">
      <div class="section-header">
        <h1 class="section-title">Upgrade to Premium</h1>
        <p class="section-subtitle">
          Unlock advanced features and accelerate your job search journey
        </p>
      </div>
      
      <!-- Loading State -->
      <div v-if="loading" class="text-center py-12">
        <div class="spinner h-16 w-16 mx-auto"></div>
        <p class="mt-4 text-gray-600 dark:text-gray-300 font-medium">Loading payment options...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="alert alert-error mb-8">
        <p>{{ error }}</p>
        <button @click="loadPlans" class="btn btn-primary mt-4">
          Retry
        </button>
      </div>

      <!-- Success Message -->
      <div v-if="successMessage" class="alert alert-success mb-8">
        <p>{{ successMessage }}</p>
      </div>

      <!-- Payment Form -->
      <div v-if="!loading && !error && !paymentProcessing" class="space-y-8">
        <!-- Plan Selection -->
        <div class="card">
          <div class="card-header">
            <h2 class="text-xl font-semibold text-gray-800 dark:text-white">Choose Your Plan</h2>
          </div>
          <div class="card-body">
            <div class="grid md:grid-cols-2 gap-6">
              <!-- Monthly Plan -->
              <div 
                :class="[
                  'border-2 rounded-xl p-6 cursor-pointer transition-all duration-300',
                  selectedPlan === 'premium_monthly' 
                    ? 'border-macaroon-peach-400 bg-gradient-to-br from-macaroon-peach-50 to-macaroon-peach-100 dark:from-macaroon-peach-900/20 dark:to-macaroon-peach-800/20 shadow-macaroon-peach' 
                    : 'border-gray-300 dark:border-gray-600 hover:border-macaroon-peach-300 hover:shadow-soft'
                ]"
                @click="selectedPlan = 'premium_monthly'"
              >
                <div class="flex items-center justify-between mb-4">
                  <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Monthly Plan</h3>
                  <div :class="[
                    'w-6 h-6 rounded-full border-2 flex items-center justify-center',
                    selectedPlan === 'premium_monthly' 
                      ? 'border-macaroon-peach-500 bg-macaroon-peach-500' 
                      : 'border-gray-300'
                  ]">
                    <div v-if="selectedPlan === 'premium_monthly'" class="w-2 h-2 bg-white rounded-full"></div>
                  </div>
                </div>
                <div class="text-3xl font-bold text-gray-900 dark:text-white mb-2">
                  ${{ plans?.premium_monthly }}<span class="text-lg text-gray-500 dark:text-gray-400">/month</span>
                </div>
                <ul class="text-sm text-gray-600 dark:text-gray-300 space-y-2">
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    Unlimited job applications tracking
                  </li>
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    Advanced interview preparation
                  </li>
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    Premium feedback portal
                  </li>
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    Priority support
                  </li>
                </ul>
              </div>

              <!-- Yearly Plan -->
              <div 
                :class="[
                  'border-2 rounded-xl p-6 cursor-pointer transition-all duration-300 relative',
                  selectedPlan === 'premium_yearly' 
                    ? 'border-macaroon-lavender-400 bg-gradient-to-br from-macaroon-lavender-50 to-macaroon-lavender-100 dark:from-macaroon-lavender-900/20 dark:to-macaroon-lavender-800/20 shadow-macaroon-lavender' 
                    : 'border-gray-300 dark:border-gray-600 hover:border-macaroon-lavender-300 hover:shadow-soft'
                ]"
                @click="selectedPlan = 'premium_yearly'"
              >
                <div class="absolute -top-3 right-4 bg-gradient-to-r from-macaroon-mint-500 to-macaroon-mint-600 text-white px-3 py-1 rounded-full text-xs font-semibold shadow-sm">
                  Best Value
                </div>
                <div class="flex items-center justify-between mb-4">
                  <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Yearly Plan</h3>
                  <div :class="[
                    'w-6 h-6 rounded-full border-2 flex items-center justify-center',
                    selectedPlan === 'premium_yearly' 
                      ? 'border-macaroon-lavender-500 bg-macaroon-lavender-500' 
                      : 'border-gray-300'
                  ]">
                    <div v-if="selectedPlan === 'premium_yearly'" class="w-2 h-2 bg-white rounded-full"></div>
                  </div>
                </div>
                <div class="text-3xl font-bold text-gray-900 dark:text-white mb-2">
                  ${{ plans?.premium_yearly }}<span class="text-lg text-gray-500 dark:text-gray-400">/year</span>
                </div>
                <div class="text-sm text-macaroon-mint-600 dark:text-macaroon-mint-400 mb-4 font-medium">
                  Save ${{ monthlySavings }} per year!
                </div>
                <ul class="text-sm text-gray-600 dark:text-gray-300 space-y-2">
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    All monthly plan features
                  </li>
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    2 months free
                  </li>
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    Advanced analytics
                  </li>
                  <li class="flex items-center">
                    <svg class="w-4 h-4 text-macaroon-mint-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    Custom integrations
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>

        <!-- Payment Method Selection -->
        <div class="card">
          <div class="card-header">
            <h2 class="text-xl font-semibold text-gray-800 dark:text-white">Payment Method</h2>
          </div>
          <div class="card-body">
            <div class="grid md:grid-cols-2 gap-4">
              <div 
                :class="[
                  'border-2 rounded-xl p-4 cursor-pointer transition-all duration-300 flex items-center',
                  selectedPaymentMethod === 'card' 
                    ? 'border-macaroon-peach-400 bg-gradient-to-r from-macaroon-peach-50 to-macaroon-peach-100 dark:from-macaroon-peach-900/20 dark:to-macaroon-peach-800/20' 
                    : 'border-gray-300 dark:border-gray-600 hover:border-macaroon-peach-300'
                ]"
                @click="selectedPaymentMethod = 'card'"
              >
                <div :class="[
                  'w-5 h-5 rounded-full border-2 flex items-center justify-center mr-3',
                  selectedPaymentMethod === 'card' 
                    ? 'border-macaroon-peach-500 bg-macaroon-peach-500' 
                    : 'border-gray-300'
                ]">
                  <div v-if="selectedPaymentMethod === 'card'" class="w-2 h-2 bg-white rounded-full"></div>
                </div>
                <div>
                  <div class="font-medium text-gray-800 dark:text-white">Credit/Debit Card</div>
                  <div class="text-sm text-gray-500 dark:text-gray-400">Visa, Mastercard, American Express</div>
                </div>
              </div>

              <div 
                :class="[
                  'border-2 rounded-xl p-4 cursor-pointer transition-all duration-300 flex items-center',
                  selectedPaymentMethod === 'paypal' 
                    ? 'border-macaroon-lavender-400 bg-gradient-to-r from-macaroon-lavender-50 to-macaroon-lavender-100 dark:from-macaroon-lavender-900/20 dark:to-macaroon-lavender-800/20' 
                    : 'border-gray-300 dark:border-gray-600 hover:border-macaroon-lavender-300'
                ]"
                @click="selectedPaymentMethod = 'paypal'"
              >
                <div :class="[
                  'w-5 h-5 rounded-full border-2 flex items-center justify-center mr-3',
                  selectedPaymentMethod === 'paypal' 
                    ? 'border-macaroon-lavender-500 bg-macaroon-lavender-500' 
                    : 'border-gray-300'
                ]">
                  <div v-if="selectedPaymentMethod === 'paypal'" class="w-2 h-2 bg-white rounded-full"></div>
                </div>
                <div>
                  <div class="font-medium text-gray-800 dark:text-white">PayPal</div>
                  <div class="text-sm text-gray-500 dark:text-gray-400">Pay with your PayPal account</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Mock Credit Card Form (for demonstration) -->
        <div v-if="selectedPaymentMethod === 'card'" class="card">
          <div class="card-header">
            <h3 class="text-xl font-semibold text-gray-800 dark:text-white">Card Information</h3>
          </div>
          <div class="card-body space-y-6">
            <div class="grid md:grid-cols-2 gap-6">
              <div class="form-group">
                <label class="form-label">
                  Card Number
                </label>
                <input
                  v-model="cardForm.number"
                  type="text"
                  placeholder="1234 5678 9012 3456"
                  class="form-control"
                >
              </div>
              <div class="form-group">
                <label class="form-label">
                  Cardholder Name
                </label>
                <input
                  v-model="cardForm.name"
                  type="text"
                  placeholder="John Doe"
                  class="form-control"
                >
              </div>
              <div class="form-group">
                <label class="form-label">
                  Expiry Date
                </label>
                <input
                  v-model="cardForm.expiry"
                  type="text"
                  placeholder="MM/YY"
                  class="form-control"
                >
              </div>
              <div class="form-group">
                <label class="form-label">
                  CVV
                </label>
                <input
                  v-model="cardForm.cvv"
                  type="text"
                  placeholder="123"
                  class="form-control"
                >
              </div>
            </div>
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-between items-center pt-6">
          <button
            @click="$router.push('/settings')"
            class="btn btn-outline"
          >
            Cancel
          </button>
          <button
            @click="processPayment"
            :disabled="!canProcessPayment"
            class="btn btn-primary"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z" />
            </svg>
            Pay ${{ selectedPlanPrice }}
          </button>
        </div>
      </div>

      <!-- Payment Processing -->
      <div v-if="paymentProcessing" class="text-center py-12">
        <div class="spinner h-16 w-16 mx-auto"></div>
        <p class="mt-4 text-gray-600 dark:text-gray-300 font-medium">Processing your payment...</p>
        <p class="text-sm text-gray-500 dark:text-gray-400">Please do not close this page</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import paymentService, { type PaymentPlan } from '../services/paymentService'

const router = useRouter()
const authStore = useAuthStore()

const plans = ref<PaymentPlan | null>(null)
const loading = ref(false)
const paymentProcessing = ref(false)
const error = ref<string | null>(null)
const successMessage = ref<string | null>(null)

const selectedPlan = ref<'premium_monthly' | 'premium_yearly'>('premium_monthly')
const selectedPaymentMethod = ref<'card' | 'paypal'>('card')

const cardForm = reactive({
  number: '',
  name: '',
  expiry: '',
  cvv: ''
})

const monthlySavings = computed(() => {
  if (!plans.value) return 0
  return Math.round((plans.value.premium_monthly * 12 - plans.value.premium_yearly) * 100) / 100
})

const selectedPlanPrice = computed(() => {
  if (!plans.value) return 0
  return plans.value[selectedPlan.value]
})

const canProcessPayment = computed(() => {
  if (selectedPaymentMethod.value === 'card') {
    return cardForm.number.length > 0 && 
           cardForm.name.length > 0 && 
           cardForm.expiry.length > 0 && 
           cardForm.cvv.length > 0
  }
  return true // PayPal doesn't require form validation here
})

async function loadPlans() {
  loading.value = true
  error.value = null
  
  try {
    const response = await paymentService.getPlans()
    if (response.success) {
      plans.value = response.data
    } else {
      error.value = response.message || 'Failed to load payment plans'
    }
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to load payment plans'
  } finally {
    loading.value = false
  }
}

async function processPayment() {
  paymentProcessing.value = true
  error.value = null
  
  try {
    // Create payment intent
    const intentResponse = await paymentService.createPaymentIntent({
      planType: selectedPlan.value,
      paymentMethod: selectedPaymentMethod.value
    })
    
    if (!intentResponse.success) {
      error.value = intentResponse.message || 'Failed to create payment intent'
      return
    }

    // Simulate payment processing delay
    await new Promise(resolve => setTimeout(resolve, 2000))

    // Confirm payment
    const confirmResponse = await paymentService.confirmPayment({
      paymentIntentId: intentResponse.data.paymentIntentId,
      planType: selectedPlan.value
    })

    if (confirmResponse.success) {
      // Update auth store with new user data
      authStore.user = {
        ...authStore.user,
        ...confirmResponse.data
      }
      
      // Update localStorage
      localStorage.setItem('user', JSON.stringify(authStore.user))
      
      successMessage.value = 'Payment successful! Your account has been upgraded to Premium.'
      
      // Redirect to settings after 3 seconds
      setTimeout(() => {
        router.push('/settings')
      }, 3000)
    } else {
      error.value = confirmResponse.message || 'Payment confirmation failed'
    }
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Payment processing failed'
  } finally {
    paymentProcessing.value = false
  }
}

onMounted(() => {
  loadPlans()
})
</script>
