<template>
  <div class="section">
    <!-- Hero Header with Greeting -->
    <div class="bg-gradient-to-r from-macaroon-peach-500 via-macaroon-lavender-500 to-macaroon-mint-500 rounded-2xl p-8 mb-8 text-white relative overflow-hidden">
      <div class="relative z-10">
        <div class="flex items-center justify-between mb-4">
          <div>
            <h1 class="text-3xl font-bold mb-2">
              {{ getGreeting() }}, {{ authStore.currentUser?.firstName || 'Champion' }}! 🚀
            </h1>
            <p class="text-lg opacity-90">{{ getMotivationalMessage() }}</p>
          </div>
          <div class="text-right">
            <div class="text-sm opacity-80">Current Streak</div>
            <div class="text-3xl font-bold">{{ currentStreak }} 🔥</div>
          </div>
        </div>
        
        <!-- Quick Progress Bar -->
        <div class="bg-white/20 rounded-full h-3 mb-3">
          <div 
            class="bg-white rounded-full h-3 transition-all duration-700"
            :style="{ width: `${Math.min(dailyProgress, 100)}%` }"
          ></div>
        </div>
        <div class="text-sm opacity-90">
          Daily Goal Progress: {{ Math.round(dailyProgress) }}% 
          <span class="font-medium">({{ completedGoals }}/{{ totalGoals }} goals completed)</span>
        </div>
      </div>
      
      <!-- Decorative Elements -->
      <div class="absolute top-0 right-0 w-32 h-32 bg-white/10 rounded-full -translate-y-16 translate-x-16"></div>
      <div class="absolute bottom-0 left-0 w-24 h-24 bg-white/10 rounded-full translate-y-12 -translate-x-12"></div>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center py-12">
      <div class="spinner h-16 w-16 mx-auto"></div>
      <p class="mt-4 text-gray-600 dark:text-gray-300 font-medium">Loading your achievements...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="alert alert-error mb-8">
      <p>{{ error }}</p>
      <button @click="fetchStats" class="btn btn-primary mt-4">
        Retry
      </button>
    </div>

    <div v-else class="space-y-8">
      <!-- Achievement Calendar & Quick Actions -->
      <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
        <!-- Achievement Calendar (3/4 width) -->
        <div class="lg:col-span-3 space-y-6">
          <!-- Calendar -->
          <div class="card">
            <div class="card-header">
              <div class="flex items-center justify-between">
                <h3 class="text-xl font-semibold text-gray-800 dark:text-white flex items-center">
                  <svg class="w-5 h-5 mr-2 text-macaroon-peach-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                  Achievement Calendar
                </h3>
                <div class="flex items-center space-x-3">
                  <button @click="previousMonth" class="p-2 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-lg">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                    </svg>
                  </button>
                  <h4 class="font-medium text-gray-800 dark:text-white min-w-[120px] text-center">
                    {{ currentDate.toLocaleString('default', { month: 'long', year: 'numeric' }) }}
                  </h4>
                  <button @click="nextMonth" class="p-2 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-lg">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                    </svg>
                  </button>
                </div>
              </div>
            </div>
            <div class="card-body">
              <!-- Calendar Grid -->
              <div class="grid grid-cols-7 gap-2 mb-6">
                <!-- Week Headers -->
                <div v-for="day in ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']" 
                     :key="day" 
                     class="text-center text-sm font-medium text-gray-500 dark:text-gray-400 p-3">
                  {{ day }}
                </div>
                
                <!-- Calendar Days -->
                <div v-for="day in calendarDays" 
                     :key="day.date.toISOString()" 
                     class="relative">
                  <div 
                    :class="{
                      'bg-gray-50 dark:bg-gray-800': !day.isCurrentMonth,
                      'bg-macaroon-peach-100 dark:bg-macaroon-peach-900 ring-2 ring-macaroon-peach-500': day.isToday,
                      'hover:bg-gray-100 dark:hover:bg-gray-700': day.isCurrentMonth
                    }"
                    class="aspect-square p-2 rounded-lg cursor-pointer transition-colors duration-200 flex flex-col items-center justify-center"
                    @click="selectDate(day.date)"
                  >
                    <div 
                      :class="{
                        'text-gray-400 dark:text-gray-600': !day.isCurrentMonth,
                        'font-bold text-macaroon-peach-600': day.isToday,
                        'text-gray-900 dark:text-gray-100': day.isCurrentMonth && !day.isToday
                      }"
                      class="text-sm mb-2"
                    >
                      {{ day.date.getDate() }}
                    </div>
                    
                    <!-- Achievement Indicators -->
                    <div v-if="day.achievements && day.achievements.length > 0" class="flex flex-wrap gap-1 justify-center">
                      <div v-for="achievement in day.achievements.slice(0, 3)" 
                           :key="achievement.type"
                           :class="getAchievementColor(achievement.type)"
                           class="w-2 h-2 rounded-full"
                           :title="`${achievement.count} ${achievement.type}`">
                      </div>
                      <div v-if="day.achievements.length > 3" 
                           class="w-2 h-2 rounded-full bg-gray-400"
                           :title="`${day.achievements.length} achievements`">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Achievement Legend -->
          <div class="card">
            <div class="card-header">
              <h4 class="text-lg font-semibold text-gray-800 dark:text-white flex items-center">
                <svg class="w-5 h-5 mr-2 text-macaroon-mint-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z" />
                </svg>
                Achievement Types
              </h4>
            </div>
            <div class="card-body">
              <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
                <div class="flex items-center space-x-3 p-3 bg-gradient-to-r from-macaroon-peach-50 to-macaroon-peach-100 dark:from-macaroon-peach-900/20 dark:to-macaroon-peach-800/20 rounded-lg">
                  <div class="w-4 h-4 bg-macaroon-peach-500 rounded-full"></div>
                  <div>
                    <div class="font-medium text-gray-900 dark:text-white">Job Applications</div>
                    <div class="text-xs text-gray-600 dark:text-gray-400">Track your applications</div>
                  </div>
                </div>
                <div class="flex items-center space-x-3 p-3 bg-gradient-to-r from-macaroon-mint-50 to-macaroon-mint-100 dark:from-macaroon-mint-900/20 dark:to-macaroon-mint-800/20 rounded-lg">
                  <div class="w-4 h-4 bg-macaroon-mint-500 rounded-full"></div>
                  <div>
                    <div class="font-medium text-gray-900 dark:text-white">Practice Problems</div>
                    <div class="text-xs text-gray-600 dark:text-gray-400">Coding challenges</div>
                  </div>
                </div>
                <div class="flex items-center space-x-3 p-3 bg-gradient-to-r from-macaroon-lavender-50 to-macaroon-lavender-100 dark:from-macaroon-lavender-900/20 dark:to-macaroon-lavender-800/20 rounded-lg">
                  <div class="w-4 h-4 bg-macaroon-lavender-500 rounded-full"></div>
                  <div>
                    <div class="font-medium text-gray-900 dark:text-white">Interview Prep</div>
                    <div class="text-xs text-gray-600 dark:text-gray-400">Practice sessions</div>
                  </div>
                </div>
                <div class="flex items-center space-x-3 p-3 bg-gradient-to-r from-macaroon-vanilla-50 to-macaroon-vanilla-100 dark:from-macaroon-vanilla-900/20 dark:to-macaroon-vanilla-800/20 rounded-lg">
                  <div class="w-4 h-4 bg-macaroon-vanilla-500 rounded-full"></div>
                  <div>
                    <div class="font-medium text-gray-900 dark:text-white">Feedback</div>
                    <div class="text-xs text-gray-600 dark:text-gray-400">Reviews & insights</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Quick Actions & Insights (1/4 width) -->
        <div class="space-y-6">
          <!-- Quick Actions -->
          <div class="card">
            <div class="card-header">
              <h3 class="text-lg font-semibold text-gray-800 dark:text-white">Quick Actions</h3>
            </div>
            <div class="card-body">
              <div class="space-y-3">
                <router-link to="/application-tracker" class="group block">
                  <div class="bg-gradient-to-r from-macaroon-peach-400 to-macaroon-peach-500 text-white p-3 rounded-xl text-center hover:shadow-macaroon-peach hover:-translate-y-1 transition-all duration-300">
                    <svg class="w-5 h-5 mx-auto mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                    </svg>
                    <div class="text-sm font-medium">Add Application</div>
                  </div>
                </router-link>
                
                <router-link to="/problem-solving" class="group block">
                  <div class="bg-gradient-to-r from-macaroon-mint-400 to-macaroon-mint-500 text-white p-3 rounded-xl text-center hover:shadow-macaroon-mint hover:-translate-y-1 transition-all duration-300">
                    <svg class="w-5 h-5 mx-auto mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364-.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                    </svg>
                    <div class="text-sm font-medium">Practice Problems</div>
                  </div>
                </router-link>
                
                <router-link to="/interview-prep" class="group block">
                  <div class="bg-gradient-to-r from-macaroon-lavender-400 to-macaroon-lavender-500 text-white p-3 rounded-xl text-center hover:shadow-macaroon-lavender hover:-translate-y-1 transition-all duration-300">
                    <svg class="w-5 h-5 mx-auto mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                    </svg>
                    <div class="text-sm font-medium">Interview Prep</div>
                  </div>
                </router-link>
                
                <router-link to="/feedback-portal" class="group block">
                  <div class="bg-gradient-to-r from-macaroon-vanilla-400 to-macaroon-vanilla-500 text-white p-3 rounded-xl text-center hover:shadow-macaroon-vanilla hover:-translate-y-1 transition-all duration-300">
                    <svg class="w-5 h-5 mx-auto mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
                    </svg>
                    <div class="text-sm font-medium">Get Feedback</div>
                  </div>
                </router-link>
              </div>
            </div>
          </div>

          <!-- Weekly Insights -->
          <div class="card">
            <div class="card-header">
              <h3 class="text-lg font-semibold text-gray-800 dark:text-white flex items-center">
                <svg class="w-5 h-5 mr-2 text-macaroon-mint-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                </svg>
                Insights
              </h3>
            </div>
            <div class="card-body space-y-4">
              <div class="p-3 bg-gradient-to-r from-macaroon-mint-50 to-macaroon-peach-50 dark:from-macaroon-mint-900/20 dark:to-macaroon-peach-900/20 rounded-lg">
                <h4 class="font-semibold text-macaroon-mint-700 dark:text-macaroon-mint-300 mb-1 text-sm">{{ getWeeklyInsight().title }}</h4>
                <p class="text-xs text-gray-600 dark:text-gray-400">{{ getWeeklyInsight().message }}</p>
              </div>
              
              <div class="space-y-3">
                <div class="text-center p-3 bg-gray-50 dark:bg-gray-800 rounded-lg">
                  <div class="text-xl font-bold text-macaroon-peach-600">{{ currentStreak }}</div>
                  <div class="text-xs text-gray-600 dark:text-gray-400">Day Streak 🔥</div>
                </div>
                <div class="text-center p-3 bg-gray-50 dark:bg-gray-800 rounded-lg">
                  <div class="text-xl font-bold text-macaroon-mint-600">{{ weeklyTotal }}</div>
                  <div class="text-xs text-gray-600 dark:text-gray-400">This Week ⚡</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Stats Overview -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div class="card hover:shadow-macaroon-peach group cursor-pointer">
          <div class="card-body text-center">
            <div class="w-12 h-12 bg-gradient-to-r from-macaroon-peach-200 to-macaroon-peach-300 rounded-full flex items-center justify-center mx-auto mb-4 group-hover:scale-110 transition-transform duration-200">
              <svg class="w-6 h-6 text-macaroon-peach-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Applications</h3>
            <p class="text-3xl font-bold text-macaroon-peach-600">{{ stats?.totalApplications || mockStats.totalApplications }}</p>
            <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ getWeeklyChange('applications') }} this week</p>
          </div>
        </div>
        
        <div class="card hover:shadow-macaroon-mint group cursor-pointer">
          <div class="card-body text-center">
            <div class="w-12 h-12 bg-gradient-to-r from-macaroon-mint-200 to-macaroon-mint-300 rounded-full flex items-center justify-center mx-auto mb-4 group-hover:scale-110 transition-transform duration-200">
              <svg class="w-6 h-6 text-macaroon-mint-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364-.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Problems Solved</h3>
            <p class="text-3xl font-bold text-macaroon-mint-600">{{ stats?.practiceProblemsCompleted || mockStats.practiceProblemsCompleted }}</p>
            <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ getWeeklyChange('problems') }} this week</p>
          </div>
        </div>
        
        <div class="card hover:shadow-macaroon-lavender group cursor-pointer">
          <div class="card-body text-center">
            <div class="w-12 h-12 bg-gradient-to-r from-macaroon-lavender-200 to-macaroon-lavender-300 rounded-full flex items-center justify-center mx-auto mb-4 group-hover:scale-110 transition-transform duration-200">
              <svg class="w-6 h-6 text-macaroon-lavender-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Interviews</h3>
            <p class="text-3xl font-bold text-macaroon-lavender-600">{{ stats?.interviewsScheduled || mockStats.interviewsScheduled }}</p>
            <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ getWeeklyChange('interviews') }} this week</p>
          </div>
        </div>
        
        <div class="card hover:shadow-macaroon-vanilla group cursor-pointer">
          <div class="card-body text-center">
            <div class="w-12 h-12 bg-gradient-to-r from-macaroon-vanilla-200 to-macaroon-vanilla-300 rounded-full flex items-center justify-center mx-auto mb-4 group-hover:scale-110 transition-transform duration-200">
              <svg class="w-6 h-6 text-macaroon-vanilla-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-800 dark:text-white mb-2">Prep Score</h3>
            <p class="text-3xl font-bold text-macaroon-vanilla-700">{{ stats?.preparationScore || mockStats.preparationScore }}%</p>
            <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ getScoreChange() }} from last week</p>
          </div>
        </div>
      </div>

      <!-- Daily Goals Section -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <!-- Today's Goals -->
        <div class="card">
          <div class="card-header">
            <h3 class="text-xl font-semibold text-gray-800 dark:text-white flex items-center">
              <svg class="w-6 h-6 mr-2 text-macaroon-peach-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
              </svg>
              Today's Goals
            </h3>
          </div>
          <div class="card-body space-y-4">
            <div v-for="goal in dailyGoals" 
                 :key="goal.id"
                 class="flex items-center space-x-3 p-3 rounded-lg border border-gray-200 dark:border-gray-700"
                 :class="{ 'bg-green-50 dark:bg-green-900/20 border-green-200 dark:border-green-800': goal.completed }">
              <button 
                @click="toggleGoal(goal.id)"
                :class="{
                  'bg-green-500 text-white': goal.completed,
                  'border-gray-300 dark:border-gray-600': !goal.completed
                }"
                class="w-6 h-6 rounded-full border-2 flex items-center justify-center transition-colors duration-200">
                <svg v-if="goal.completed" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd" />
                </svg>
              </button>
              <div class="flex-1">
                <p :class="{ 'line-through text-gray-500': goal.completed }" class="font-medium">{{ goal.title }}</p>
                <p class="text-sm text-gray-600 dark:text-gray-400">{{ goal.description }}</p>
              </div>
              <div :class="getAchievementColor(goal.type)" class="w-3 h-3 rounded-full"></div>
            </div>
            
            <button class="w-full p-3 border-2 border-dashed border-gray-300 dark:border-gray-600 rounded-lg text-gray-500 dark:text-gray-400 hover:border-macaroon-peach-400 hover:text-macaroon-peach-600 transition-colors duration-200">
              <svg class="w-5 h-5 mx-auto mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
              </svg>
              Add Custom Goal
            </button>
          </div>
        </div>

        <!-- Selected Date Details -->
        <div class="card">
          <div class="card-header">
            <h3 class="text-xl font-semibold text-gray-800 dark:text-white">
              {{ selectedDate ? `Achievements for ${selectedDate.toLocaleDateString()}` : 'Select a Date' }}
            </h3>
          </div>
          <div class="card-body">
            <div v-if="!selectedDate" class="text-center py-8 text-gray-500 dark:text-gray-400">
              <svg class="w-12 h-12 mx-auto mb-4 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <p class="font-medium">Click on a calendar date</p>
              <p class="text-sm">to view achievements for that day</p>
            </div>
            <div v-else-if="selectedDateAchievements && selectedDateAchievements.length === 0" class="text-center py-8 text-gray-500 dark:text-gray-400">
              <svg class="w-12 h-12 mx-auto mb-4 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <p class="font-medium">No achievements recorded</p>
              <p class="text-sm">for this date</p>
            </div>
            <div v-else-if="selectedDateAchievements" class="space-y-3">
              <div v-for="achievement in selectedDateAchievements" 
                   :key="achievement.type"
                   class="flex items-center space-x-3 p-3 bg-gray-50 dark:bg-gray-800 rounded-lg">
                <div :class="getAchievementColor(achievement.type)" class="w-8 h-8 rounded-full flex items-center justify-center">
                  <svg class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd" />
                  </svg>
                </div>
                <div>
                  <h4 class="font-medium text-gray-900 dark:text-white">{{ formatAchievementType(achievement.type) }}</h4>
                  <p class="text-sm text-gray-600 dark:text-gray-400">{{ achievement.count }} completed</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import dashboardService from '../services/dashboardService'
import type { DashboardStats } from '../services/dashboardService'

const authStore = useAuthStore()
const loading = ref(false)
const error = ref<string | null>(null)
const stats = ref<DashboardStats | null>(null)

// Calendar and Date Management
const currentDate = ref(new Date())
const selectedDate = ref<Date | null>(null)

// Achievement and Goal Tracking
const currentStreak = ref(8)
const weeklyTotal = ref(27)
const achievements = ref<Map<string, any[]>>(new Map())

// Mock realistic stats that align with achievements and goals
const mockStats = ref({
  totalApplications: 23,
  activeApplications: 12,
  interviewsScheduled: 5,
  practiceProblemsCompleted: 87,
  feedbackReceived: 4,
  preparationScore: 84
})

// Daily Goals
const dailyGoals = ref([
  { id: 1, title: 'Apply to 3 new positions', description: 'Focus on mid-level roles at tech companies', type: 'applications', completed: true },
  { id: 2, title: 'Complete 4 LeetCode problems', description: 'Focus on medium difficulty algorithms', type: 'problems', completed: true },
  { id: 3, title: 'Practice behavioral questions', description: 'Prepare STAR method responses', type: 'interview', completed: false },
  { id: 4, title: 'Review technical concepts', description: 'System design fundamentals', type: 'interview', completed: false },
  { id: 5, title: 'Update portfolio projects', description: 'Add recent work and improve descriptions', type: 'networking', completed: false }
])

// Computed Properties
const dailyProgress = computed(() => {
  if (dailyGoals.value.length === 0) return 0
  const completed = dailyGoals.value.filter(goal => goal.completed).length
  return (completed / dailyGoals.value.length) * 100
})

const completedGoals = computed(() => dailyGoals.value.filter(goal => goal.completed).length)
const totalGoals = computed(() => dailyGoals.value.length)

const selectedDateAchievements = computed(() => {
  if (!selectedDate.value) return null
  const dateKey = selectedDate.value.toISOString().split('T')[0]
  return achievements.value.get(dateKey) || []
})

const calendarDays = computed(() => {
  const days = []
  const year = currentDate.value.getFullYear()
  const month = currentDate.value.getMonth()
  
  // First day of the month
  const firstDay = new Date(year, month, 1)
  // Last day of the month
  const lastDay = new Date(year, month + 1, 0)
  
  // Start from the Sunday before the first day
  const startDate = new Date(firstDay)
  startDate.setDate(firstDay.getDate() - firstDay.getDay())
  
  // End on the Saturday after the last day
  const endDate = new Date(lastDay)
  endDate.setDate(lastDay.getDate() + (6 - lastDay.getDay()))
  
  const current = new Date(startDate)
  while (current <= endDate) {
    const isCurrentMonth = current.getMonth() === month
    const isToday = current.toDateString() === new Date().toDateString()
    const dateKey = current.toISOString().split('T')[0]
    const dayAchievements = achievements.value.get(dateKey) || []
    
    days.push({
      date: new Date(current),
      isCurrentMonth,
      isToday,
      achievements: dayAchievements
    })
    
    current.setDate(current.getDate() + 1)
  }
  
  return days
})

// Methods
function getGreeting() {
  const hour = new Date().getHours()
  if (hour < 12) return 'Good morning'
  if (hour < 17) return 'Good afternoon'
  return 'Good evening'
}

function getMotivationalMessage() {
  const messages = [
    "Today is a new opportunity to advance your career! 💪",
    "Every application brings you closer to your dream job! ✨",
    "Your consistency is building towards something amazing! 🌟",
    "Keep pushing forward - success is within reach! 🚀",
    "Today's efforts are tomorrow's achievements! 💎"
  ]
  return messages[Math.floor(Math.random() * messages.length)]
}

function getAchievementColor(type: string): string {
  const colors = {
    applications: 'bg-macaroon-peach-500',
    problems: 'bg-macaroon-mint-500',
    interview: 'bg-macaroon-lavender-500',
    feedback: 'bg-macaroon-vanilla-500',
    networking: 'bg-macaroon-blush-500'
  }
  return colors[type as keyof typeof colors] || 'bg-gray-500'
}

function formatAchievementType(type: string): string {
  const types = {
    applications: 'Job Applications',
    problems: 'Practice Problems',
    interview: 'Interview Preparation',
    feedback: 'Feedback Sessions',
    networking: 'Networking Activities'
  }
  return types[type as keyof typeof types] || type
}

function previousMonth() {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, 1)
}

function nextMonth() {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 1)
}

function selectDate(date: Date) {
  selectedDate.value = date
}

function toggleGoal(goalId: number) {
  const goal = dailyGoals.value.find(g => g.id === goalId)
  if (goal) {
    goal.completed = !goal.completed
  }
}

function getWeeklyChange(type: string): string {
  // Mock data for weekly changes that reflect realistic progress
  const changes = {
    applications: '+5',
    problems: '+18',
    interviews: '+2'
  }
  return changes[type as keyof typeof changes] || '+0'
}

function getScoreChange(): string {
  return '+8%'
}

function getWeeklyInsight() {
  const insights = [
    {
      title: "Outstanding Progress! 🎉",
      message: "You're crushing your goals! 5 applications this week shows real dedication."
    },
    {
      title: "Coding Champion! 💡",
      message: "87 problems solved total! Your algorithmic skills are getting sharper every day."
    },
    {
      title: "Interview Mastery! 🎯",
      message: "5 interviews scheduled - your preparation is paying off. Keep that momentum!"
    },
    {
      title: "Consistency Pays! ⚡",
      message: "8-day streak! Your daily practice routine is building incredible momentum."
    },
    {
      title: "Career Growth! 🚀",
      message: "84% prep score - you're in the top tier of job seekers. Companies will notice!"
    }
  ]
  return insights[Math.floor(Math.random() * insights.length)]
}

// Initialize sample achievement data
function initializeAchievements() {
  const today = new Date()
  const achievements = new Map()
  
  // Add achievements for the past 2 weeks to show more activity
  for (let i = 0; i < 14; i++) {
    const date = new Date(today)
    date.setDate(today.getDate() - i)
    const dateKey = date.toISOString().split('T')[0]
    
    const dayAchievements = []
    
    // More realistic achievement patterns
    // Applications: about 1-3 per day on weekdays
    if (date.getDay() >= 1 && date.getDay() <= 5 && Math.random() > 0.2) {
      dayAchievements.push({ type: 'applications', count: Math.floor(Math.random() * 3) + 1 })
    }
    
    // Problems: most days, varying amounts
    if (Math.random() > 0.15) {
      dayAchievements.push({ type: 'problems', count: Math.floor(Math.random() * 6) + 2 })
    }
    
    // Interview prep: less frequent but consistent
    if (Math.random() > 0.6) {
      dayAchievements.push({ type: 'interview', count: Math.floor(Math.random() * 2) + 1 })
    }
    
    // Feedback: occasional
    if (Math.random() > 0.85) {
      dayAchievements.push({ type: 'feedback', count: 1 })
    }
    
    // Networking: occasional
    if (Math.random() > 0.75) {
      dayAchievements.push({ type: 'networking', count: 1 })
    }
    
    if (dayAchievements.length > 0) {
      achievements.set(dateKey, dayAchievements)
    }
  }
  
  return achievements
}

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
  achievements.value = initializeAchievements()
  selectedDate.value = new Date() // Select today by default
})
</script>
