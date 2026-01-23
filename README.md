# Online Learning Platform (Enterprise Edition)

A comprehensive enterprise-grade online learning platform built with modern technologies and best practices.

## 🏗️ Architecture

### Technology Stack
- **Backend**: Spring Boot 3.2, Java 21, Spring Security (JWT), Spring Data JPA
- **Frontend**: React 18, Tailwind CSS, React Router
- **Database**: MySQL 8.0 with Flyway migrations
- **Build**: Maven (multi-module)
- **Testing**: JUnit 5, Mockito, Testcontainers
- **Documentation**: OpenAPI 3.0 (Swagger)
- **Code Quality**: Lombok, MapStruct, Validation

### Project Structure
```
online-learning-platform/
├── backend/                 # Spring Boot API
│   ├── src/main/java/
│   │   └── com/udemy/clone/
│   │       ├── entity/      # JPA Entities
│   │       ├── repository/  # Data Access Layer
│   │       ├── service/     # Business Logic
│   │       ├── controller/  # REST Controllers
│   │       ├── dto/         # Data Transfer Objects
│   │       ├── mapper/      # MapStruct Mappers
│   │       ├── config/      # Configuration
│   │       └── security/    # Security & JWT
│   └── src/main/resources/
│       └── db/migration/    # Flyway SQL Scripts
├── frontend/               # React Application
└── docker-compose.yml      # Development Environment
```

## 🚀 Features

### User Roles & Permissions
- **STUDENT**: Browse courses, enroll, take quizzes, track progress
- **INSTRUCTOR**: Create courses, manage content, view analytics
- **ADMIN**: Full platform management, user administration

### Core Functionality
- 🔐 JWT Authentication with refresh tokens
- 📚 Course management with rich content
- 🎯 Interactive quizzes with scoring
- 📊 Progress tracking and analytics
- 🔍 Advanced search and filtering
- 📱 Responsive design

## 🛠️ Development Setup

### Prerequisites
- Java 21
- Node.js 18+
- MySQL 8.0
- Maven 3.8+

### Database Setup
```sql
CREATE DATABASE learning_platform;
```

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

## 📚 API Documentation

Once the backend is running, visit:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with Testcontainers
mvn test -Dspring.profiles.active=test
```

## 🐳 Docker Deployment

```bash
docker-compose up -d
```

## 📋 Database Schema

- **users**: User accounts with RBAC
- **courses**: Course catalog with metadata
- **lessons**: Course content and videos
- **quizzes**: Interactive assessments
- **enrollments**: Student-course relationships
- **quiz_submissions**: Quiz attempts and scores
- **refresh_tokens**: JWT token management

## 🔒 Security Features

- BCrypt password hashing
- JWT access & refresh tokens
- Role-based access control (RBAC)
- Method-level security with @PreAuthorize
- CORS configuration
- Input validation

## 🚀 Future Enhancements

- Certificate generation
- Video streaming
- Real-time notifications
- Payment integration
- Mobile application
- Advanced analytics
- Discussion forums

## 📄 License

MIT License