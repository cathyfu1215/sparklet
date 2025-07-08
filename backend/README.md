# Sparklet Backend

A Java Spring Boot REST API for the Sparklet career development platform.

## Features

- **Authentication & Authorization**: JWT-based authentication with Spring Security
- **Dashboard**: Career development statistics and metrics
- **Problem Solving**: Coding problems with solutions and hints
- **Interview Preparation**: Interview questions with suggested answers
- **Application Tracking**: Job application management
- **Feedback System**: Peer feedback and reviews
- **User Management**: Profile management and settings

## Tech Stack

- Java 17
- Spring Boot 3.2.1
- Spring Security 6
- JWT for authentication
- Maven for dependency management
- Mock data (no database required)

## API Endpoints

### Authentication
- `POST /api/auth/signin` - User login
- `POST /api/auth/signup` - User registration

### Dashboard
- `GET /api/dashboard/stats` - Get dashboard statistics

### Job Applications
- `GET /api/applications` - Get user's applications
- `POST /api/applications` - Create new application
- `PUT /api/applications/{id}` - Update application
- `DELETE /api/applications/{id}` - Delete application

### Problem Solving
- `GET /api/problems` - Get all problems (with optional filters)
- `GET /api/problems/{id}` - Get specific problem
- `POST /api/problems` - Create new problem
- `PUT /api/problems/{id}` - Update problem
- `DELETE /api/problems/{id}` - Delete problem

### Interview Preparation
- `GET /api/interview/questions` - Get interview questions (with optional filters)
- `GET /api/interview/questions/{id}` - Get specific question
- `POST /api/interview/questions` - Create new question
- `PUT /api/interview/questions/{id}` - Update question
- `DELETE /api/interview/questions/{id}` - Delete question

### Feedback
- `GET /api/feedback/received` - Get received feedback
- `GET /api/feedback/given` - Get given feedback
- `GET /api/feedback/{id}` - Get specific feedback
- `POST /api/feedback` - Create new feedback
- `PUT /api/feedback/{id}` - Update feedback
- `DELETE /api/feedback/{id}` - Delete feedback

### User Profile
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update user profile

### Test Endpoints
- `GET /api/test/public` - Public endpoint (no auth required)
- `GET /api/test/user` - Protected endpoint (auth required)

## Getting Started

### Prerequisites
- Java 17 or later
- Maven 3.6 or later

### Installation

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080/api`

### Configuration

The application uses the following default configuration:
- Server port: 8080
- Context path: /api
- JWT secret: Auto-generated secure key
- JWT expiration: 24 hours
- CORS: Enabled for frontend development

### Mock Data

The application includes mock data for:
- **Users**: 3 sample users (tom@example.com, jane@example.com, john@example.com)
- **Problems**: 5 coding problems with different difficulties
- **Interview Questions**: 8 interview questions of various types
- **Job Applications**: 5 sample applications
- **Feedback**: 5 sample feedback entries

Default login credentials:
- Email: `tom@example.com`
- Password: `password123`

### API Response Format

All API responses follow this format:
```json
{
  "success": true,
  "message": "Success message",
  "data": { /* response data */ }
}
```

### Authentication

Protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <jwt-token>
```

## Development

### Project Structure
```
src/main/java/com/sparklet/
├── SparkletBackendApplication.java
├── config/
│   └── WebSecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── DashboardController.java
│   ├── JobApplicationController.java
│   ├── ProblemController.java
│   ├── InterviewController.java
│   ├── FeedbackController.java
│   ├── UserController.java
│   └── TestController.java
├── dto/
│   ├── ApiResponse.java
│   └── auth/
│       ├── AuthResponse.java
│       ├── LoginRequest.java
│       └── SignupRequest.java
├── model/
│   ├── User.java
│   ├── JobApplication.java
│   ├── Problem.java
│   ├── InterviewQuestion.java
│   ├── Feedback.java
│   └── DashboardStats.java
├── security/
│   ├── JwtUtils.java
│   ├── UserPrincipal.java
│   ├── AuthTokenFilter.java
│   └── AuthEntryPointJwt.java
└── service/
    ├── UserDetailsServiceImpl.java
    ├── DashboardService.java
    ├── JobApplicationService.java
    ├── ProblemService.java
    ├── InterviewService.java
    └── FeedbackService.java
```

### Testing

Test the API using tools like:
- Postman
- curl
- Frontend application

Example login request:
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email": "tom@example.com", "password": "password123"}'
```

## Future Enhancements

- Database integration (PostgreSQL/MySQL)
- Email verification
- Password reset functionality
- File upload for profile pictures
- Advanced search and filtering
- Notification system
- Rate limiting
- API documentation with Swagger
