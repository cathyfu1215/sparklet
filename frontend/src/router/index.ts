import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('../views/WelcomeView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('../views/DashboardView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/problem-solving',
      name: 'problem-solving',
      component: () => import('../views/ProblemSolvingView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/interview-prep',
      name: 'interview-prep',
      component: () => import('../views/InterviewPrepView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/feedback-portal',
      name: 'feedback-portal',
      component: () => import('../views/FeedbackPortalView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/application-tracker',
      name: 'application-tracker',
      component: () => import('../views/ApplicationTrackerView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/settings',
      name: 'settings',
      component: () => import('../views/SettingsView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/payment',
      name: 'payment',
      component: () => import('../views/PaymentView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('../views/SignupView.vue'),
      meta: { requiresAuth: false }
    },
  ],
})

// Route guard for authentication
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    // Redirect to login if route requires auth and user is not authenticated
    next('/login')
  } else if ((to.name === 'login' || to.name === 'signup') && authStore.isAuthenticated) {
    // Redirect to dashboard if trying to access login/signup while authenticated
    next('/dashboard')
  } else {
    next()
  }
})

export default router