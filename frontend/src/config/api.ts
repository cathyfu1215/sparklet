// API Configuration
export const API_BASE_URL = 'http://localhost:8080/api'

// API endpoints
export const API_ENDPOINTS = {
  // Auth
  LOGIN: '/auth/signin',
  SIGNUP: '/auth/signup',
  
  // Dashboard
  DASHBOARD_STATS: '/dashboard/stats',
  
  // Job Applications
  APPLICATIONS: '/applications',
  
  // Problems
  PROBLEMS: '/problems',
  
  // Interview
  INTERVIEW_QUESTIONS: '/interview/questions',
  
  // Feedback
  FEEDBACK: '/feedback',
  FEEDBACK_RECEIVED: '/feedback/received',
  
  // User
  USER_PROFILE: '/user/profile',
  
  // Payment
  PAYMENT_CREATE_INTENT: '/payment/create-intent',
  PAYMENT_CONFIRM: '/payment/confirm',
  PAYMENT_PLANS: '/payment/plans',
  
  // Pages
  PAGES: '/pages',
  PAGE_INFO: '/pages',
  
  // Test
  TEST_PUBLIC: '/test/public',
  TEST_USER: '/test/user'
}
