# Hospital Management API

A production-ready RESTful API for hospital management built with Spring Boot, Spring Security, and PostgreSQL.

## Features

### 🔐 Security
- JWT Authentication (stateless)
- Role-based access control (ADMIN, DOCTOR, PATIENT)
- Password encryption with BCrypt
- CSRF protection disabled for REST API

### 👨‍💼 Admin Operations (`/admin`)
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/admin/doctors` | POST | Add new doctor |
| `/admin/doctors` | GET | List all doctors |
| `/admin/doctors/department/{id}` | GET | Doctors by department |
| `/admin/departments` | POST | Create department |
| `/admin/departments` | GET | List all departments |
| `/admin/departments/{id}` | GET | Get department |
| `/admin/departments/{id}/head-doctor/{doctorId}` | PATCH | Assign head doctor |

### 👨‍⚕️ Doctor Operations (`/doctors`)
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/doctors/{id}` | GET | View doctor profile |
| `/doctors/{id}/appointments` | GET | View appointments |
| `/doctors/{id}/appointments/{appointmentId}/complete` | PATCH | Complete appointment |

### 🧑‍🤒 Patient Operations (`/patients`)
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/patients` | POST | Register patient |
| `/patients/{id}` | GET | Get patient |
| `/patients/{id}/appointments` | POST | Book appointment |
| `/patients/{id}/appointments` | GET | View appointments |
| `/patients/{id}/appointments/{appointmentId}/cancel` | PATCH | Cancel appointment |
| `/patients/{id}/insurance` | POST | Add insurance |
| `/patients/{id}/insurance` | GET | View insurance |

---

## Business Rules

| Rule | Description |
|------|-------------|
| **Unique Emails** | Patients and doctors can't share emails |
| **No Double-Booking** | 30-minute appointment slots prevent conflicts |
| **Ownership Validation** | Patients can only cancel their own appointments |
| **Status Transitions** | Only SCHEDULED appointments can be cancelled/completed |
| **Head Doctor Rule** | Head doctor must belong to the same department |
| **Single Insurance** | One patient can have only one insurance policy |

---

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.5 |
| Spring Security | 6.x |
| Spring Data JPA | 3.x |
| PostgreSQL | 12+ |
| Lombok | Latest |

---

## Project Structure

```
src/main/java/com/hospitalapi/
├── controller/        # REST Controllers (3)
│   ├── AdminController
│   ├── DoctorController
│   └── PatientController
├── service/           # Business Logic
│   └── impl/          # Service Implementations
├── repository/        # Data Access (5)
├── entity/            # JPA Entities (5)
│   └── enums/         # Gender, BloodGroup, AppointmentStatus
├── dto/               # Request/Response DTOs (10)
├── exception/         # Global Exception Handler
└── security/          # Security Configuration
```

---

## Data Models

### Entities
- **Patient** - name, email, birthDate, gender, bloodGroup, insurance
- **Doctor** - name, email, specialization, department
- **Department** - name, headDoctor
- **Appointment** - appointmentTime, status, reason
- **Insurance** - policyNumber, provider, validUntil

### Enums
- **Gender**: `MALE`, `FEMALE`, `OTHER`
- **BloodGroupType**: `A_POSITIVE`, `A_NEGATIVE`, `B_POSITIVE`, `B_NEGATIVE`, `AB_POSITIVE`, `AB_NEGATIVE`, `O_POSITIVE`, `O_NEGATIVE`
- **AppointmentStatus**: `SCHEDULED`, `COMPLETED`, `CANCELLED`

---

## Getting Started

### 1. Database Setup
```sql
CREATE DATABASE hospital;
```

### 2. Configure `application.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hospital
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
server:
  servlet:
    context-path: /api/v1
```

### 3. Run Application
```bash
./mvnw spring-boot:run
```

### 4. Access API
```
http://localhost:8080/api/v1
```

---

## Error Handling

Standardized error response format:
```json
{
  "success": false,
  "message": "Error message",
  "errors": {"field": "validation error"},
  "status": 400,
  "timestamp": "2026-01-02T10:30:00"
}
```

| Status | Description |
|--------|-------------|
| 200 | Success |
| 400 | Bad Request / Validation Error |
| 404 | Resource Not Found |
| 409 | Conflict (duplicate) |
| 500 | Server Error |

---

## Request Examples

### Register Patient
```json
POST /api/v1/patients
{
  "name": "John Doe",
  "birthDate": "1990-05-15",
  "email": "john@example.com",
  "gender": "MALE",
  "bloodGroup": "O_POSITIVE"
}
```

### Book Appointment
```json
POST /api/v1/patients/1/appointments
{
  "doctorId": 2,
  "appointmentTime": "2026-01-15T10:30:00",
  "reason": "Regular checkup"
}
```

### Add Insurance
```json
POST /api/v1/patients/1/insurance
{
  "policyNumber": "INS123456",
  "provider": "HealthCare Plus",
  "validUntil": "2027-12-31"
}
```

---



✅ Clean REST API design  
✅ Service layer pattern  
✅ DTO pattern  
✅ Global exception handling  
✅ Input validation  
✅ JPA relationships  
✅ Real-world business logic  
✅ Transaction management  
✅ Spring Security configuration  

---


