# Hospital Management API

A comprehensive RESTful API for managing hospital operations including patients, doctors, departments, appointments, and insurance information. Built with Spring Boot and PostgreSQL.

## Features

- **Patient Management**: Create, read, update, and delete patient records
- **Doctor Management**: Manage doctor profiles and department assignments
- **Department Management**: Organize departments and assign head doctors
- **Appointment Scheduling**: Book, cancel, and complete appointments
- **Insurance Management**: Add and manage patient insurance information
- **Advanced Querying**: Filter appointments and search patients

## Tech Stack

- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA (Hibernate)
- **Build Tool**: Maven/Gradle
- **Validation**: Jakarta Validation
- **Mapping**: ModelMapper

## Prerequisites

- Java 17 or higher
- PostgreSQL 12+
- Maven 3.6+ or Gradle 7+

## Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE hospital;
```

2. Update database credentials in `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hospital
    username: your_username
    password: your_password
```

## Installation & Running

1. Clone the repository:
```bash
git clone <repository-url>
cd hospital-api
```

2. Build the project:
```bash
# Using Maven
mvn clean install

# Using Gradle
gradle build
```

3. Run the application:
```bash
# Using Maven
mvn spring-boot:run

# Using Gradle
gradle bootRun

# Using JAR
java -jar target/hospital-api-0.0.1-SNAPSHOT.jar
```

The API will start on `http://localhost:8080`

## API Endpoints

### Patient Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/patients` | Create a new patient |
| GET | `/patients/{id}` | Get patient by ID |
| GET | `/patients` | Get all patients |
| PUT | `/patients/{id}` | Update patient |
| DELETE | `/patients/{id}` | Delete patient |

**Request Body Example**:
```json
{
  "name": "John Doe",
  "birthDate": "1990-05-15",
  "email": "john.doe@example.com",
  "gender": "MALE",
  "bloodGroup": "O_POSITIVE"
}
```

### Doctor Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/doctors` | Create a new doctor |
| GET | `/doctors/{id}` | Get doctor by ID |
| GET | `/doctors?departmentId={id}` | Get doctors by department |

**Request Body Example**:
```json
{
  "name": "Dr. Smith",
  "specialization": "Cardiology",
  "email": "dr.smith@hospital.com",
  "departmentId": 1
}
```

### Department Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/departments` | Create a department |
| GET | `/departments` | Get all departments |
| PATCH | `/departments/{id}/head-doctor/{doctorId}` | Assign head doctor |

**Request Body Example**:
```json
{
  "name": "Cardiology"
}
```

### Appointment Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/appointments` | Create appointment |
| GET | `/appointments/{id}` | Get appointment by ID |
| GET | `/appointments/patients/{patientId}` | Get patient's appointments |
| GET | `/appointments/doctors/{doctorId}` | Get doctor's appointments |
| PATCH | `/appointments/{id}/cancel` | Cancel appointment |
| PATCH | `/appointments/{id}/complete` | Complete appointment |

**Request Body Example**:
```json
{
  "patientId": 1,
  "doctorId": 2,
  "appointmentTime": "2026-01-15T10:30:00",
  "reason": "Regular checkup"
}
```

### Insurance Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/patients/{patientId}/insurance` | Add insurance |
| GET | `/patients/{patientId}/insurance` | Get patient's insurance |
| DELETE | `/patients/{patientId}/insurance` | Remove insurance |

**Request Body Example**:
```json
{
  "policyNumber": "INS123456",
  "provider": "HealthCare Plus",
  "validUntil": "2027-12-31"
}
```

## Data Models

### Enums

**Gender**: `MALE`, `FEMALE`, `OTHER`

**BloodGroupType**: `A_POSITIVE`, `A_NEGATIVE`, `B_POSITIVE`, `B_NEGATIVE`, `AB_POSITIVE`, `AB_NEGATIVE`, `O_POSITIVE`, `O_NEGATIVE`

**AppointmentStatus**: `SCHEDULED`, `COMPLETED`, `CANCELLED`

**RoleType**: `ADMIN`, `DOCTOR`, `PATIENT`

## Error Handling

The API uses a global exception handler that returns standardized error responses:

```json
{
  "success": false,
  "message": "Error message",
  "errors": {
    "field": "validation error"
  },
  "status": 400,
  "timestamp": "2026-01-02T10:30:00"
}
```

### Common HTTP Status Codes

- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `204 No Content`: Successful deletion
- `400 Bad Request`: Invalid request data
- `404 Not Found`: Resource not found
- `409 Conflict`: Duplicate resource
- `500 Internal Server Error`: Server error

## Business Rules

1. **Department Names**: Must be unique
2. **Email Addresses**: Must be unique for patients and doctors
3. **Head Doctor Assignment**: Head doctor must belong to the same department
4. **Insurance**: One patient can have only one insurance policy
5. **Appointment Status**: Can be SCHEDULED, COMPLETED, or CANCELLED

## Database Schema

The application uses the following main entities:

- **Patient**: Stores patient information with one-to-one relationship to Insurance
- **Doctor**: Stores doctor information with many-to-one relationship to Department
- **Department**: Stores department information with one-to-one relationship to head doctor
- **Appointment**: Stores appointment information with many-to-one relationships to Patient and Doctor
- **Insurance**: Stores insurance information with one-to-one relationship to Patient

## Configuration

### JPA Configuration

The application uses `create-drop` DDL strategy which recreates the database schema on each startup. For production, change this to:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

### ModelMapper Configuration

ModelMapper is configured with `skipNullEnabled(true)` to avoid overwriting existing values with nulls during mapping.

## Development

### Adding New Features

1. Create entity in `com.hospitalapi.entity`
2. Create repository in `com.hospitalapi.repository`
3. Create DTOs in `com.hospitalapi.dto`
4. Create service interface in `com.hospitalapi.service`
5. Create service implementation in `com.hospitalapi.service.impl`
6. Create controller in `com.hospitalapi.controller`

### Testing

Run tests using:
```bash
# Maven
mvn test

# Gradle
gradle test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/hospitalapi/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities
│   │   │   └── enums/       # Enum types
│   │   ├── exception/       # Exception handling
│   │   ├── repository/      # Data repositories
│   │   └── service/         # Business logic
│   │       └── impl/        # Service implementations
│   └── resources/
│       └── application.yml  # Application configuration
└── test/                    # Test classes
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please contact the development team.

## Future Enhancements

- [ ] Authentication and Authorization (Spring Security)
- [ ] Role-based access control
- [ ] Appointment conflict detection
- [ ] Email notifications
- [ ] Medical records management
- [ ] Billing system integration
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Pagination for list endpoints
- [ ] Advanced search and filtering
- [ ] Audit logging
