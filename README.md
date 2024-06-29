# Spring Boot Starter
Starter Spring Boot server application that provides a login and registration system.
It uses Spring Security for authentication, with JWT (JSON Web Token) and BCrypt.
The authentication token can be provided through the request header using the Bearer token format or HTTP-only cookie.


## Features
- Spring Security
- JWT Authentication
- BCrypt password hashing
- HTTP-Only Cookies


## Endpoints
- **POST: /api/register**
  ```json
  {
    "fullname": "Example User",
    "email": "example@gmail.com",
    "password": "12345678"
  }
  ```
- **POST: /api/log-in**
  ```json
  {
    "email": "example@gmail.com",
    "password": "12345678"
  }
  ```
- **GET: /api/authenticate**
  - Retrieve user data
  - Requires authentication
- **GET: /api/log-out**
  - Log out by removing HTTP-only cookies
- **GET: /api/users**
  - Retrieve list of users
- **GET: /api/users/csv**
  - Download list of users in csv file
- **GET: /api/users/:id**
  - Retrieve chosen user by id
