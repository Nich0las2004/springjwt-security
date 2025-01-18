# SpringJWT-Security

This project demonstrates the implementation of JWT Authentication and Authorization using Spring Security. It includes features like token management, secure API endpoints, and user management with role-based access control.

## Features

### JWT Authentication:
- **JWT Generation & Validation**: Uses Spring Security and JWT to generate and validate tokens.
- **Roles & Permissions**: User roles and permissions are included in the JWT claims.

### Secure API Endpoints:
- **Role-Based Authorization**: Endpoints are protected using authentication and role-based authorization.
- **Fine-Grained Access Control**: Uses `@PreAuthorize` annotations to restrict access to certain endpoints based on roles.

### Token Management:
- **Token Issuance & Refresh**: Endpoints for issuing new tokens and refreshing expired tokens.
- **Token Expiration & Revocation**: Tokens expire after a certain period, and revocation functionality is provided.

### User Management:
- **User Registration**: Endpoints to register new users.
- **Login**: Endpoint to log in and receive JWT tokens.
- **User Details**: Endpoint to retrieve user information.
- **Secure Handling**: Sensitive data is handled securely with proper encryption.

### Testing:
- The API has been tested using Postman, covering scenarios with valid, invalid, and expired tokens.

## Endpoints

The following API endpoints are available:

- `POST /api/register`: Registers a new user.
- `POST /api/login`: Logs in a user and returns JWT token.
- `GET /api/user`: Retrieves details of the authenticated user.
- `POST /api/token/refresh`: Refreshes an expired access token using a refresh token.
- `POST /api/token/revoke`: Revokes a user's token, invalidating it immediately.
- `GET /api/admin/dashboard`: Accessible only by users with the `ADMIN` role.

## Technologies Used
- **Spring Boot** for backend development.
- **Spring Security** for authentication and authorization.
- **JWT (JSON Web Tokens)** for secure token-based authentication.
- **H2 Database** for in-memory data storage.

## Testing the API
- **Register a User:** POST /api/register
- **Login and Get JWT:** POST /api/login
- **Access Secure AP:** GET /api/user (Requires valid JWT)
- **Refresh Access Token:** POST /api/token/refresh
- **Revoke Access Token:** POST /api/token/revoke

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Nich0las2004/springjwt-security.git
   ```
2. **Navigate to the project directory:**
   ```
   cd springjwt-security
   ```
3. **Build the project using Maven:**
   ```
   ./mvnw clean install
   ```
4. **Run the application:**
   ```
   ./mvnw spring-boot:run
   ```
**The application will start on http://localhost:8080.**

## Notes
- This application uses in-memory H2 database, which means data will be lost once the application is restarted.
- JWT secret keys are dynamically generated within the code and are not hardcoded in the application.properties file.
- Make sure to secure your JWT signing key appropriately for production.