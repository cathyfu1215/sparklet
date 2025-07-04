import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: () => import('../views/DashboardView.vue'),
    },
    {
      path: '/problem-solving',
      name: 'problem-solving',
      component: () => import('../views/ProblemSolvingView.vue'),
    },
    {
      path: '/interview-prep',
      name: 'interview-prep',
      component: () => import('../views/InterviewPrepView.vue'),
    },
    {
      path: '/feedback-portal',
      name: 'feedback-portal',
      component: () => import('../views/FeedbackPortalView.vue'),
    },
    {
      path: '/application-tracker',
      name: 'application-tracker',
      component: () => import('../views/ApplicationTrackerView.vue'),
    },
    {
      path: '/settings',
      name: 'settings',
      component: () => import('../views/SettingsView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
  ],
})

export default router