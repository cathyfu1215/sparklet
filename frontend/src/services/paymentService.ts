import apiService from './apiService'
import { API_ENDPOINTS } from '../config/api'
import type { ApiResponse } from './authService'

export interface PaymentPlan {
  premium_monthly: number
  premium_yearly: number
}

export interface PaymentIntentRequest {
  planType: 'premium_monthly' | 'premium_yearly'
  paymentMethod: 'card' | 'paypal'
}

export interface PaymentIntentResponse {
  clientSecret: string
  paymentIntentId: string
  amount: number
  currency: string
  status: string
}

export interface PaymentConfirmRequest {
  paymentIntentId: string
  planType: 'premium_monthly' | 'premium_yearly'
}

class PaymentService {
  async getPlans(): Promise<ApiResponse<PaymentPlan>> {
    return await apiService.get<ApiResponse<PaymentPlan>>(API_ENDPOINTS.PAYMENT_PLANS)
  }

  async createPaymentIntent(request: PaymentIntentRequest): Promise<ApiResponse<PaymentIntentResponse>> {
    return await apiService.post<ApiResponse<PaymentIntentResponse>>(API_ENDPOINTS.PAYMENT_CREATE_INTENT, request)
  }

  async confirmPayment(request: PaymentConfirmRequest): Promise<ApiResponse<any>> {
    return await apiService.post<ApiResponse<any>>(API_ENDPOINTS.PAYMENT_CONFIRM, request)
  }
}

export default new PaymentService()
