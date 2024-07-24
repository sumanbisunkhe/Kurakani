
# KURAKANI: Chat Application

This is a Spring Boot WebSocket chat application that allows users to register, send connection requests to each other, and chat once the request is accepted. The application uses JWT authentication for secure communication.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing the APIs](#testing-the-apis)
- [Project Structure](#project-structure)
- [Contact Information](#contact-information)

## Features
- User registration with OTP verification
- User login with JWT authentication
- Sending and accepting connection requests
- Real-time chat functionality using WebSocket
- JWT-based authentication for WebSocket communication
- User profile management with email and username updates requiring OTP verification

## Technologies Used
- Spring Boot
- Spring Security
- Spring WebSocket
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Tokens)
- Maven

## Getting Started
### Prerequisites
- Java 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL 13 or higher

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/sumanbisunkhe/Spring-Boot-Web-Socket.git
   cd Spring-Boot-Web-Socket
   ```

2. Configure the database in `application.properties`:
   ```properties
    spring.application.name=Kurakani
    spring.datasource.url=jdbc:postgresql://localhost:5432/kurakani_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=your_email
    spring.mail.password=your_email's_app_password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mail.properties.mail.smtp.starttls.required=true
    spring.mail.properties.mail.smtp.connectiontimeout=5000
    spring.mail.properties.mail.smtp.timeout=5000
    spring.mail.properties.mail.smtp.writetimeout=5000


   

3. Build the application:
   ```bash
   mvn clean install
   ```

## Configuration
- Ensure your PostgreSQL database is running and accessible with the credentials provided in the `application.properties` file.
- Configure JWT secret in `application.properties`.

## Running the Application
1. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```

2. Access the application at `http://localhost:8080`.

## Testing the APIs
### User Controller
1. **Register User**: 
   ```http
   POST /api/register
   Content-Type: application/json
   {
     "name": "John Doe",
     "username": "john_doe",
     "email": "john.doe@example.com",
     "password": "password123",
     "gender": "MALE",
     "age": 30,
     "country": "USA"
   }
   ```

2. **Verify OTP**: 
   ```http
   POST /api/verify-otp
   Content-Type: application/json
   {
     "username": "john_doe",
     "otpCode": "123456"
   }
   ```

3. **Login**: 
   ```http
   POST /api/login
   Content-Type: application/json
   {
     "username": "john_doe",
     "password": "password123"
   }
   ```

4. **Update User**: 
   ```http
   PUT /api/user
   Authorization: Bearer <JWT_TOKEN>
   Content-Type: application/json
   {
     "username": "john_doe_new",
     "email": "john.doe_new@example.com",
     "name": "John Doe",
     "gender": "MALE",
     "age": 31,
     "country": "USA"
   }
   ```

5. **Get User Details**: 
   ```http
   GET /api/user
   Authorization: Bearer <JWT_TOKEN>
   ```

### Connection Controller
1. **Send Connection Request**: 
   ```http
   POST /api/connections/request
   Authorization: Bearer <JWT_TOKEN>
   Content-Type: application/json
   {
     "senderUsername": "john_doe",
     "receiverUsername": "jane_doe"
   }
   ```

2. **Accept Connection Request**: 
   ```http
   POST /api/connections/accept
   Authorization: Bearer <JWT_TOKEN>
   Content-Type: application/json
   {
     "senderUsername": "john_doe",
     "receiverUsername": "jane_doe"
   }
   ```

3. **Get Connection Requests**: 
   ```http
   GET /api/connections/requests
   Authorization: Bearer <JWT_TOKEN>
   ```

4. **Get Connections**: 
   ```http
   GET /api/connections
   Authorization: Bearer <JWT_TOKEN>
   ```

### Message Controller
1. **Send Message**: 
   ```http
   POST /api/messages
   Authorization: Bearer <JWT_TOKEN>
   Content-Type: application/json
   {
     "fromUser": "john_doe",
     "toUser": "jane_doe",
     "text": "Hello, Jane!"
   }
   ```

2. **Get Messages**: 
   ```http
   GET /api/messages
   Authorization: Bearer <JWT_TOKEN>
   ```

## Project Structure
```plaintext
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── example/
│   │           └── kurakani/
│   │               ├── config/        # Configuration classes
│   │               ├── controller/    # REST controllers
│   │               ├── dto/           # Data Transfer Objects (DTOs)
│   │               ├── model/         # Entity models
│   │               ├── repository/    # JPA repositories
│   │               ├── security/      # Security configuration
│   │               ├── service/       # Service classes
│   │               └── websocket/     # WebSocket configuration and handlers
│   ├── resources/
│   │   ├── static/                    # Static resources (CSS, JS)
│   │   ├── templates/                 # Thymeleaf templates
│   │   └── application.properties     # Application configuration
│   └── webapp/                        # Web application files
└── test/
    └── java/
        └── org/
            └── example/
                └── kurakani/
                    └── ChatApplicationTests.java  # Test cases
```

## Contact Information
For any inquiries or support, please contact:
- Suman Bisunkhe
- Email: [sumanbisunkhe304@gmail.com](mailto:sumanbisunkhe304@gmail.com)

---

Feel free to reach out if you have any questions or need further assistance.
