# Online Learning Platform

A comprehensive microservices-based online learning platform built with Spring Boot backend and React frontend.

## Architecture

### Microservices
- **User Service** (Port 8081) - Authentication and user management
- **Course Service** (Port 8082) - Course creation and management
- **Lesson Service** (Port 8083) - Lesson content management
- **Quiz Service** (Port 8084) - Quiz creation and submission
- **Enrollment Service** (Port 8085) - Course enrollment and progress tracking
- **API Gateway** (Port 8080) - Routes requests to appropriate services

### Frontend
- **React Application** (Port 3000) - User interface for all roles

## Features

### User Roles
- **Student**: Browse courses, enroll, view lessons, take quizzes
- **Instructor**: Create courses, add lessons, manage content
- **Admin**: Publish courses, manage platform

### Core Functionality
- User authentication with JWT tokens
- Course catalog with filtering and pagination
- Interactive lessons with video support
- Quiz system with automatic scoring
- Progress tracking for enrolled students
- Role-based access control

## Prerequisites

- Java 21
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+

## Database Setup

Create the following MySQL databases:

```sql
CREATE DATABASE user_db;
CREATE DATABASE course_db;
CREATE DATABASE lesson_db;
CREATE DATABASE quiz_db;
CREATE DATABASE enrollment_db;
```

## Configuration

Update the database credentials in each service's `application.properties`:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

### Backend Services

1. Start each microservice in order:

```bash
# User Service
cd user-service
mvn spring-boot:run

# Course Service
cd course-service
mvn spring-boot:run

# Lesson Service
cd lesson-service
mvn spring-boot:run

# Quiz Service
cd quiz-service
mvn spring-boot:run

# Enrollment Service
cd enrollment-service
mvn spring-boot:run

# API Gateway
cd api-gateway
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

## API Endpoints

### User Service (8081)
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `GET /api/users/{id}` - Get user by ID

### Course Service (8082)
- `GET /api/courses` - Get published courses (with pagination and filtering)
- `POST /api/courses` - Create course (INSTRUCTOR)
- `PUT /api/courses/{id}` - Update course (owner or ADMIN)
- `POST /api/courses/{id}/publish` - Publish course (ADMIN)
- `GET /api/courses/{id}` - Get course details

### Lesson Service (8083)
- `GET /api/lessons/course/{courseId}` - Get lessons by course
- `GET /api/lessons/{id}` - Get lesson details
- `POST /api/lessons` - Create lesson
- `PUT /api/lessons/{id}` - Update lesson

### Quiz Service (8084)
- `GET /api/quizzes/lesson/{lessonId}` - Get quiz by lesson
- `POST /api/quizzes` - Create quiz
- `POST /api/quizzes/submit` - Submit quiz answers

### Enrollment Service (8085)
- `POST /api/enrollments` - Enroll in course
- `GET /api/enrollments/user/{userId}` - Get user enrollments
- `PUT /api/enrollments/progress/{courseId}` - Update progress

## Usage

1. **Registration**: Create an account as Student, Instructor, or Admin
2. **Login**: Authenticate to access role-specific features
3. **Browse Courses**: View available courses in the catalog
4. **Enroll**: Students can enroll in published courses
5. **Create Content**: Instructors can create courses and lessons
6. **Take Quizzes**: Complete quizzes to test knowledge
7. **Admin Functions**: Admins can publish courses and manage the platform

## Technology Stack

### Backend
- Spring Boot 3.2.0
- Spring Security (JWT Authentication)
- Spring Data JPA
- MySQL Database
- Spring Cloud Gateway

### Frontend
- React 18
- React Router
- Bootstrap 5
- Axios for API calls

## Project Structure

```
online-learning-platform/
├── user-service/           # User management and authentication
├── course-service/         # Course management
├── lesson-service/         # Lesson content management
├── quiz-service/          # Quiz functionality
├── enrollment-service/    # Enrollment and progress tracking
├── api-gateway/          # API Gateway for routing
├── frontend/             # React frontend application
└── pom.xml              # Parent Maven configuration
```

## Future Enhancements

- Video streaming integration
- Certificate generation
- Advanced progress analytics
- Discussion forums
- Mobile application
- Payment integration
- Advanced quiz types
- Live streaming capabilities