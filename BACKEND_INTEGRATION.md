# Sparklet Backend Integration Guide

## Overview

I've created a comprehensive Java Spring Boot backend for the Sparklet career development platform that supports all the frontend features observed in your Vue.js application.

## Backend Architecture

### Core Features Built

1. **Authentication System**
   - JWT-based authentication with Spring Security
   - User registration and login endpoints
   - Protected routes with token validation

2. **Dashboard Analytics**
   - Career development statistics
   - Job application metrics
   - Problem-solving progress tracking

3. **Job Application Tracker**
   - Full CRUD operations for job applications
   - Status tracking (APPLIED, INTERVIEW, OFFER, REJECTED, ACCEPTED)
   - User-specific application management

4. **Problem Solving Platform**
   - Coding problems with multiple difficulty levels
   - Category-based organization (ALGORITHMS, DATA_STRUCTURES, SYSTEM_DESIGN)
   - Solutions and hints for each problem

5. **Interview Preparation**
   - Various question types (BEHAVIORAL, TECHNICAL, SYSTEM_DESIGN)
   - Suggested answers and tips
   - Difficulty-based filtering

6. **Feedback System**
   - Peer feedback and reviews
   - Different feedback types (PEER_REVIEW, MOCK_INTERVIEW, CODE_REVIEW)
   - Rating system (1-5 stars)

7. **User Profile Management**
   - Profile updates
   - User settings
   - Profile photo management

## API Integration Points

### Frontend Routes → Backend Endpoints

| Frontend Route | Backend Endpoints | Description |
|---------------|------------------|-------------|
| `/` (Dashboard) | `GET /api/dashboard/stats` | Dashboard statistics |
| `/login` | `POST /api/auth/signin` | User authentication |
| `/signup` | `POST /api/auth/signup` | User registration |
| `/application-tracker` | `GET/POST/PUT/DELETE /api/applications` | Job applications CRUD |
| `/problem-solving` | `GET/POST/PUT/DELETE /api/problems` | Coding problems |
| `/interview-prep` | `GET/POST/PUT/DELETE /api/interview/questions` | Interview questions |
| `/feedback-portal` | `GET/POST/PUT/DELETE /api/feedback` | Feedback system |
| `/settings` | `GET/PUT /api/user/profile` | User profile management |

## Mock Data Included

The backend includes realistic mock data for development:

- **3 Sample Users** with credentials (tom@example.com / password123)
- **5 Job Applications** with different statuses
- **5 Coding Problems** across various categories and difficulties  
- **8 Interview Questions** covering behavioral, technical, and system design
- **5 Feedback Entries** with ratings and reviews

## How to Run

1. **Prerequisites**: Java 17+ and Maven 3.6+

2. **Start Backend**:
   ```bash
   cd backend
   ./run.sh
   # OR
   mvn spring-boot:run
   ```

3. **Backend will run on**: `http://localhost:8080/api`

4. **Test API**:
   ```bash
   # Test public endpoint
   curl http://localhost:8080/api/test/public
   
   # Login to get JWT token
   curl -X POST http://localhost:8080/api/auth/signin \
     -H "Content-Type: application/json" \
     -d '{"email": "tom@example.com", "password": "password123"}'
   ```

## Frontend Integration Steps

To connect your Vue.js frontend with this backend:

1. **Update API Base URL**:
   ```javascript
   const API_BASE_URL = 'http://localhost:8080/api'
   ```

2. **Add HTTP Client** (e.g., Axios):
   ```bash
   cd frontend
   npm install axios
   ```

3. **Create API Service**:
   ```javascript
   // services/api.js
   import axios from 'axios'
   
   const api = axios.create({
     baseURL: 'http://localhost:8080/api',
     headers: {
       'Content-Type': 'application/json'
     }
   })
   
   // Add JWT token to requests
   api.interceptors.request.use(config => {
     const token = localStorage.getItem('token')
     if (token) {
       config.headers.Authorization = `Bearer ${token}`
     }
     return config
   })
   
   export default api
   ```

4. **Update Frontend Views**:
   - Replace placeholder content with API calls
   - Add data fetching in component mounted hooks
   - Implement form submissions for creating/updating data

## Security Features

- **CORS enabled** for frontend development
- **JWT tokens** with 24-hour expiration
- **Password encryption** using BCrypt
- **Protected endpoints** requiring authentication
- **User-specific data** filtering

## API Response Format

All endpoints return consistent response format:
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* actual data */ }
}
```

## Development Benefits

- **No database setup required** - uses in-memory mock data
- **Hot reload** with Spring Boot DevTools
- **Comprehensive logging** for debugging
- **Ready for database integration** when needed
- **Production-ready security** configuration

## Next Steps

1. **Start the backend** using the provided run script
2. **Update frontend** to consume these APIs
3. **Test integration** with the provided mock data
4. **Customize data structures** as needed for your specific requirements

The backend is now ready to support your Vue.js frontend with all the career development features!
