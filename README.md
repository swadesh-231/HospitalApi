# 🏥 Hospital Management REST API

A production-ready, secure RESTful API for hospital management built with **Java 21**, **Spring Boot 3.5**, and **PostgreSQL**. Features JWT authentication, OAuth2 Google login, and role-based access control.

---

## 📊 Project Statistics

| Metric | Count |
|--------|-------|
| **REST Endpoints** | 19 |
| **Java Files** | 64 |
| **Controllers** | 4 |
| **Services** | 14 |
| **JPA Entities** | 6 |
| **DTOs** | 14 |
| **Repositories** | 6 |

---

## ✨ Key Features

### 🔐 Security & Authentication
- **JWT Authentication** - Stateless token-based authentication
- **OAuth2 Google Login** - Sign in with Google integration
- **Role-Based Access Control (RBAC)** - 3 roles: ADMIN, DOCTOR, PATIENT
- **Password Encryption** - BCrypt hashing
- **Stateless Sessions** - No server-side session storage

### 👨‍💼 Admin Operations
- Create, view, and manage doctors
- Create and manage hospital departments
- Assign head doctors to departments
- View doctors by department

### 👨‍⚕️ Doctor Operations
- View personal profile
- View assigned appointments
- Mark appointments as completed

### 🏥 Patient Operations
- Register patient profile
- Book appointments with doctors
- View and cancel appointments
- Add and manage insurance information

### 📖 API Documentation
- **Swagger UI** - Interactive API documentation
- **OpenAPI 3.0** - Standardized API specification

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Core Language |
| Spring Boot | 3.5.x | Application Framework |
| Spring Security | 6.x | Authentication & Authorization |
| Spring Data JPA | 3.x | Data Persistence |
| PostgreSQL | 12+ | Production Database |
| H2 Database | 2.x | Test Database |
| JWT (jjwt) | 0.13.x | Token Generation |
| SpringDoc OpenAPI | 2.8.x | API Documentation |
| Lombok | Latest | Boilerplate Reduction |
| Maven | 3.x | Build Tool |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENT (Postman/Frontend)              │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    SECURITY LAYER                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ JwtAuthFilter│  │OAuth2Handler│  │ SecurityConfig      │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    CONTROLLER LAYER                         │
│  ┌────────────┐ ┌────────────┐ ┌─────────┐ ┌─────────────┐  │
│  │AuthController│ │AdminController│ │DoctorController│ │PatientController│ │
│  └────────────┘ └────────────┘ └─────────┘ └─────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                            │
│  AuthService, DoctorService, PatientService,                │
│  AppointmentService, DepartmentService, InsuranceService    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   REPOSITORY LAYER                          │
│  UserRepository, DoctorRepository, PatientRepository,       │
│  AppointmentRepository, DepartmentRepository, InsuranceRepo │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    DATABASE (PostgreSQL)                    │
└─────────────────────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
src/main/java/com/hospitalapi/
├── config/              # Configuration classes
│   └── OpenApiConfig    # Swagger/OpenAPI configuration
├── controller/          # REST Controllers
│   ├── AuthController   # Authentication endpoints
│   ├── AdminController  # Admin operations
│   ├── DoctorController # Doctor operations
│   └── PatientController# Patient operations
├── dto/                 # Data Transfer Objects
│   ├── LoginRequest/Response
│   ├── SignUpRequest/Response
│   ├── CreateDoctorRequest/DoctorResponse
│   ├── CreatePatientRequest/PatientResponse
│   ├── CreateAppointmentRequest/AppointmentResponse
│   └── ...14 DTOs total
├── entity/              # JPA Entities
│   ├── User             # Authentication user
│   ├── Patient          # Patient information
│   ├── Doctor           # Doctor information
│   ├── Department       # Hospital departments
│   ├── Appointment      # Patient-Doctor appointments
│   ├── Insurance        # Patient insurance
│   └── enums/           # RoleType, AuthProvider, etc.
├── exception/           # Global Exception Handler
├── repository/          # JPA Repositories
├── security/            # Security Configuration
│   ├── SecurityConfig   # Main security config
│   ├── jwt/             # JWT Service & Filter
│   └── handler/         # OAuth2 & Auth handlers
└── service/             # Business Logic
    └── impl/            # Service implementations
```

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- PostgreSQL 12+
- Maven 3.x

### 1. Clone & Configure

Create a `.env` file:
```env
DB_URL=jdbc:postgresql://localhost:5432/hospital
DB_USERNAME=postgres
DB_PASSWORD=your_password
JPA_DDL_AUTO=update
SERVER_CONTEXT_PATH=/api/v1
JWT_SECRET=your_secret_key_minimum_32_characters_long
JWT_ACCESS_TOKEN_EXP_MS=3600000
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_secret
```

### 2. Run Application
```bash
./mvnw spring-boot:run
```

### 3. Access API
- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api/v1/v3/api-docs`

---

## 📋 API Endpoints

### 🔓 Authentication (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login & get JWT |
| GET | `/oauth2/authorization/google` | Google OAuth login |

### 👑 Admin Endpoints (ADMIN Role)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/doctors` | Create doctor |
| GET | `/admin/doctors` | List all doctors |
| GET | `/admin/doctors/department/{id}` | Doctors by department |
| POST | `/admin/departments` | Create department |
| GET | `/admin/departments` | List departments |
| GET | `/admin/departments/{id}` | Get department |
| PATCH | `/admin/departments/{id}/head-doctor/{doctorId}` | Assign head doctor |

### 🩺 Doctor Endpoints (DOCTOR Role)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/doctors/{id}` | Get doctor profile |
| GET | `/doctors/{id}/appointments` | View appointments |
| PATCH | `/doctors/{id}/appointments/{apptId}/complete` | Complete appointment |

### 🏥 Patient Endpoints (PATIENT Role)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/patients` | Register patient |
| GET | `/patients/{id}` | Get patient profile |
| POST | `/patients/{id}/appointments` | Book appointment |
| GET | `/patients/{id}/appointments` | View appointments |
| PATCH | `/patients/{id}/appointments/{apptId}/cancel` | Cancel appointment |
| POST | `/patients/{id}/insurance` | Add insurance |
| GET | `/patients/{id}/insurance` | Get insurance |

---

## 🔒 Security Implementation

### Role-Based Access Control
```
ADMIN  → /admin/**
DOCTOR → /doctors/**
PATIENT → /patients/**
```

### JWT Token Flow
1. User registers/logs in → Server generates JWT
2. Client stores JWT → Sends in Authorization header
3. JwtAuthFilter validates token → Sets SecurityContext
4. Controller method executes if authorized

### OAuth2 Google Flow
1. User clicks "Login with Google"
2. Redirect to Google OAuth consent screen
3. Google authenticates → Redirects back with code
4. Server exchanges code → Creates/finds user → Returns JWT

---

## 📝 Business Rules

| Rule | Description |
|------|-------------|
| **Unique Emails** | Patients and doctors cannot share emails |
| **No Double-Booking** | 30-minute slots prevent appointment conflicts |
| **Ownership Validation** | Users can only modify their own resources |
| **Status Transitions** | Only SCHEDULED appointments can be cancelled/completed |
| **Head Doctor Rule** | Head doctor must belong to the same department |
| **Single Insurance** | One patient can have only one active insurance |

---

## ⚠️ Error Handling

Standardized JSON error responses:
```json
{
  "success": false,
  "message": "Error description",
  "errors": {"field": "validation message"},
  "status": 400,
  "timestamp": "2026-01-03T10:30:00"
}
```

| Status | Description |
|--------|-------------|
| 200 | Success |
| 400 | Bad Request / Validation Error |
| 401 | Unauthorized (invalid/missing token) |
| 403 | Forbidden (insufficient role) |
| 404 | Resource Not Found |
| 409 | Conflict (duplicate resource) |
| 500 | Internal Server Error |

---

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report
```

Tests use H2 in-memory database for isolation.

---




## 🔮 Future Enhancements

- [ ] Add email notifications for appointments
- [ ] Implement refresh token mechanism
- [ ] Add appointment reminders
- [ ] Integrate payment gateway
- [ ] Add medical records management
- [ ] Implement rate limiting
- [ ] Add Docker containerization
- [ ] Set up CI/CD pipeline

---

## 👨‍💻 Author

Built with ❤️ using Spring Boot

