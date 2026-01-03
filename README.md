# Hospital Management API

A production-ready RESTful API for hospital management built with Spring Boot, featuring JWT authentication, OAuth2 Google login, and Role-Based Access Control (RBAC).

## 🚀 Features

- **JWT Authentication** - Stateless token-based auth
- **OAuth2 Google Login** - Sign in with Google
- **Role-Based Access Control** - ADMIN, DOCTOR, PATIENT roles
- **RESTful API Design** - Clean endpoint structure
- **Global Exception Handling** - Standardized error responses

---

## 🛠️ Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.5 |
| Spring Security | 6.x |
| Spring Data JPA | 3.x |
| PostgreSQL | 12+ |
| JWT (jjwt) | 0.12.x |
| Lombok | Latest |

---

## 📦 Getting Started

### 1. Clone & Configure

Create a `.env` file in the project root:

```env
# Database
DB_URL=jdbc:postgresql://localhost:5432/hospital_db
DB_USERNAME=postgres
DB_PASSWORD=your_password
JPA_DDL_AUTO=update

# Server
SERVER_CONTEXT_PATH=/api

# JWT (secret must be 32+ characters)
JWT_SECRET=your_super_secret_key_at_least_32_chars
JWT_ACCESS_TOKEN_EXP_MS=3600000

# Google OAuth2 (optional)
GOOGLE_CLIENT_ID=your-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your-secret
```

### 2. Run Application

```bash
./mvnw spring-boot:run
```

### 3. Access API

```
http://localhost:8080/api
```

---

## � Swagger UI (Interactive API Docs)

Once the app is running, open in your browser:

```
http://localhost:8080/api/swagger-ui.html
```

**Features:**
- 🔍 Browse all API endpoints
- 🔐 Authorize with JWT token (click "Authorize" button)
- 🧪 Test endpoints directly from the browser

---

## �🔐 Authentication

### Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "pass123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "pass123"}'
```
**Response:** `{"jwt": "eyJhbGc...", "userId": 1}`

### Google OAuth2 Login
Open in browser: `http://localhost:8080/api/oauth2/authorization/google`

### Using JWT Token
```bash
curl http://localhost:8080/api/patients/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 🔒 Role-Based Access Control

| Role | Endpoints | Assigned |
|------|-----------|----------|
| **PATIENT** | `/patients/**` | Default for new users |
| **DOCTOR** | `/doctors/**` | Manual assignment |
| **ADMIN** | `/admin/**` | Manual assignment |

> New users (signup/OAuth2) automatically receive the **PATIENT** role.

---

## 📋 API Endpoints

### Auth (`/auth`) - Public
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login & get JWT |

### Admin (`/admin`) - ADMIN Role Required
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/doctors` | Create doctor |
| GET | `/admin/doctors` | List all doctors |
| GET | `/admin/doctors/department/{id}` | Doctors by department |
| POST | `/admin/departments` | Create department |
| GET | `/admin/departments` | List departments |
| GET | `/admin/departments/{id}` | Get department |
| PATCH | `/admin/departments/{id}/head-doctor/{doctorId}` | Assign head doctor |

### Doctor (`/doctors`) - DOCTOR Role Required
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/doctors/{id}` | Get doctor profile |
| GET | `/doctors/{id}/appointments` | View appointments |
| PATCH | `/doctors/{id}/appointments/{apptId}/complete` | Complete appointment |

### Patient (`/patients`) - PATIENT Role Required
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/patients` | Register patient profile |
| GET | `/patients/{id}` | Get patient profile |
| POST | `/patients/{id}/appointments` | Book appointment |
| GET | `/patients/{id}/appointments` | View appointments |
| PATCH | `/patients/{id}/appointments/{apptId}/cancel` | Cancel appointment |
| POST | `/patients/{id}/insurance` | Add insurance |
| GET | `/patients/{id}/insurance` | Get insurance |

---

## 📁 Project Structure

```
src/main/java/com/hospitalapi/
├── controller/          # REST Controllers
│   ├── AuthController
│   ├── AdminController
│   ├── DoctorController
│   └── PatientController
├── service/             # Business Logic
│   └── impl/
├── repository/          # JPA Repositories
├── entity/              # JPA Entities
│   └── enums/           # RoleType, AuthProvider, etc.
├── dto/                 # Request/Response DTOs
├── exception/           # Global Exception Handler
└── security/
    ├── SecurityConfig   # Security configuration
    ├── jwt/             # JWT Service
    ├── filter/          # JwtAuthFilter
    └── handler/         # OAuth2SuccessHandler, AuthEntryPointJwt
```

---

## 🗄️ Data Models

### Entities
- **User** - username, email, password, roles, authProvider
- **Patient** - name, email, birthDate, gender, bloodGroup
- **Doctor** - name, email, specialization, department
- **Department** - name, headDoctor
- **Appointment** - appointmentTime, status, reason
- **Insurance** - policyNumber, provider, validUntil

### Enums
- **RoleType**: `ADMIN`, `DOCTOR`, `PATIENT`
- **AuthProvider**: `EMAIL`, `GOOGLE`, `GITHUB`, `FACEBOOK`
- **AppointmentStatus**: `SCHEDULED`, `COMPLETED`, `CANCELLED`

---

## ⚠️ Error Response Format

```json
{
  "success": false,
  "message": "Error message",
  "errors": {"field": "validation error"},
  "status": 400,
  "timestamp": "2026-01-03T10:30:00"
}
```

| Status | Description |
|--------|-------------|
| 200 | Success |
| 400 | Bad Request / Validation Error |
| 401 | Unauthorized |
| 403 | Forbidden (role mismatch) |
| 404 | Not Found |
| 500 | Server Error |

---

## 🧪 Quick Test

```bash
# 1. Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "test", "password": "pass123"}'

# 2. Login (save the JWT)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "test", "password": "pass123"}'

# 3. Access patient endpoint (should work - PATIENT role)
curl http://localhost:8080/api/patients/1 \
  -H "Authorization: Bearer YOUR_JWT"

# 4. Access admin endpoint (should fail - 403 Forbidden)
curl http://localhost:8080/api/admin/doctors \
  -H "Authorization: Bearer YOUR_JWT"
```

---

## ✅ Production Checklist

- [x] JWT Authentication
- [x] OAuth2 Google Login
- [x] Role-Based Access Control
- [x] Service Layer Pattern
- [x] DTO Pattern
- [x] Global Exception Handling
- [x] Input Validation
- [x] JPA Relationships
- [x] Transaction Management
- [x] Spring Security Configuration

---


