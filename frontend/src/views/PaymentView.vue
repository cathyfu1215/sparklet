<template>
  <div class="max-w-4xl mx-auto">
    <div class="bg-white dark:bg-gray-800 shadow-md rounded-lg p-6">
      <h1 class="text-3xl font-bold text-gray-800 dark:text-white mb-6">Upgrade to Premium</h1>
      
      <!-- Loading State -->
      <div v-if="loading" class="text-center py-8">
        <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-500 mx-auto"></div>
        <p class="mt-4 text-gray-600 dark:text-gray-300">Loading payment options...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
        <p>{{ error }}</p>
        <button @click="loadPlans" class="mt-2 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
          Retry
        </button>
      </div>

      <!-- Success Message -->
      <div v-if="successMessage" class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
        <p>{{ successMessage }}</p>
      </div>

      <!-- Payment Form -->
      <div v-if="!loading && !error && !paymentProcessing" class="space-y-6">
        <!-- Plan Selection -->
        <div>
          <h2 class="text-xl font-semibold text-gray-800 dark:text-white mb-4">Choose Your Plan</h2>
          <div class="grid md:grid-cols-2 gap-6">
            <!-- Monthly Plan -->
            <div 
              :class="[
                'border-2 rounded-lg p-6 cursor-pointer transition-colors',
                selectedPlan === 'premium_monthly' 
                  ? 'border-blue-500 bg-blue-50 dark:bg-blue-900/20' 
                  : 'border-gray-300 dark:border-gray-600 hover:border-blue-300'
              ]"
              @click="selectedPlan = 'premium_monthly'"
            >
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Monthly Plan</h3>
                <input 
                  type="radio" 
                  :checked="selectedPlan === 'premium_monthly'"
                  class="text-blue-500 focus:ring-blue-500"
                  readonly
                >
              </div>
              <div class="text-3xl font-bold text-gray-900 dark:text-white mb-2">
                ${{ plans?.premium_monthly }}<span class="text-lg text-gray-500">/month</span>
              </div>
              <ul class="text-sm text-gray-600 dark:text-gray-300 space-y-1">
                <li>✓ Unlimited job applications tracking</li>
                <li>✓ Advanced interview preparation</li>
                <li>✓ Premium feedback portal</li>
                <li>✓ Priority support</li>
              </ul>
            </div>

            <!-- Yearly Plan -->
            <div 
              :class="[
                'border-2 rounded-lg p-6 cursor-pointer transition-colors relative',
                selectedPlan === 'premium_yearly' 
                  ? 'border-blue-500 bg-blue-50 dark:bg-blue-900/20' 
                  : 'border-gray-300 dark:border-gray-600 hover:border-blue-300'
              ]"
              @click="selectedPlan = 'premium_yearly'"
            >
              <div class="absolute -top-3 right-4 bg-green-500 text-white px-3 py-1 rounded-full text-xs font-semibold">
                Best Value
              </div>
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Yearly Plan</h3>
                <input 
                  type="radio" 
                  :checked="selectedPlan === 'premium_yearly'"
                  class="text-blue-500 focus:ring-blue-500"
                  readonly
                >
              </div>
              <div class="text-3xl font-bold text-gray-900 dark:text-white mb-2">
                ${{ plans?.premium_yearly }}<span class="text-lg text-gray-500">/year</span>
              </div>
              <div class="text-sm text-green-600 dark:text-green-400 mb-2 font-medium">
                Save ${{ monthlySavings }} per year!
              </div>
              <ul class="text-sm text-gray-600 dark:text-gray-300 space-y-1">
                <li>✓ All monthly plan features</li>
                <li>✓ 2 months free</li>
                <li>✓ Advanced analytics</li>
                <li>✓ Custom integrations</li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Payment Method Selection -->
        <div>
          <h2 class="text-xl font-semibold text-gray-800 dark:text-white mb-4">Payment Method</h2>
          <div class="grid md:grid-cols-2 gap-4">
            <div 
              :class="[
                'border-2 rounded-lg p-4 cursor-pointer transition-colors flex items-center',
                selectedPaymentMethod === 'card' 
                  ? 'border-blue-500 bg-blue-50 dark:bg-blue-900/20' 
                  : 'border-gray-300 dark:border-gray-600 hover:border-blue-300'
              ]"
              @click="selectedPaymentMethod = 'card'"
            >
              <input 
                type="radio" 
                :checked="selectedPaymentMethod === 'card'"
                class="text-blue-500 focus:ring-blue-500 mr-3"
                readonly
              >
              <div>
                <div class="font-medium text-gray-800 dark:text-white">Credit/Debit Card</div>
                <div class="text-sm text-gray-500">Visa, Mastercard, American Express</div>
              </div>
            </div>

            <div 
              :class="[
                'border-2 rounded-lg p-4 cursor-pointer transition-colors flex items-center',
                selectedPaymentMethod === 'paypal' 
                  ? 'border-blue-500 bg-blue-50 dark:bg-blue-900/20' 
                  : 'border-gray-300 dark:border-gray-600 hover:border-blue-300'
              ]"
              @click="selectedPaymentMethod = 'paypal'"
            >
              <input 
                type="radio" 
                :checked="selectedPaymentMethod === 'paypal'"
                class="text-blue-500 focus:ring-blue-500 mr-3"
                readonly
              >
              <div>
                <div class="font-medium text-gray-800 dark:text-white">PayPal</div>
                <div class="text-sm text-gray-500">Pay with your PayPal account</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Mock Credit Card Form (for demonstration) -->
        <div v-if="selectedPaymentMethod === 'card'" class="space-y-4">
          <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Card Information</h3>
          <div class="grid md:grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Card Number
              </label>
              <input
                v-model="cardForm.number"
                type="text"
                placeholder="1234 5678 9012 3456"
                class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              >
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Cardholder Name
              </label>
              <input
                v-model="cardForm.name"
                type="text"
                placeholder="John Doe"
                class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              >
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Expiry Date
              </label>
              <input
                v-model="cardForm.expiry"
                type="text"
                placeholder="MM/YY"
                class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              >
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                CVV
              </label>
              <input
                v-model="cardForm.cvv"
                type="text"
                placeholder="123"
                class="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              >
            </div>
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-between items-center pt-6 border-t border-gray-200 dark:border-gray-600">
          <button
            @click="$router.push('/settings')"
            class="px-6 py-2 border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-50 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Cancel
          </button>
          <button
            @click="processPayment"
            :disabled="!canProcessPayment"
            class="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            Pay ${{ selectedPlanPrice }}
          </button>
        </div>
      </div>

      <!-- Payment Processing -->
      <div v-if="paymentProcessing" class="text-center py-8">
        <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-500 mx-auto"></div>
        <p class="mt-4 text-gray-600 dark:text-gray-300">Processing your payment...</p>
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
