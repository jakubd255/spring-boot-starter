# Spring Boot Starter
Starter Spring Boot server application that provides a login and registration system.
It uses Spring Security for authentication, with JWT (JSON Web Token) and BCrypt.
The authentication token can be provided through the request header using the Bearer token format or HTTP-only cookie.  
Additionally, it is possible to upload and download files.

## Features
- Spring Security
- Session-based authentication
- Email verification
- BCrypt password hashing
- HTTP-Only Cookies
- File upload and download

## Endpoints
### POST: /api/auth/register
```json
{
  "fullName": "Example User",
  "email": "example@gmail.com",
  "password": "12345678"
}
```
- Registers new user

### POST: /api/auth/log-in
```json
{
  "email": "example@gmail.com",
  "password": "12345678"
}
```
- Log in a user
- Returns a `sessionId` in the response and sets an HTTP-only cookie

### POST: /api/auth/verify?token=?
- Verifies the user’s email using a token
- Returns a `sessionId` upon successful verification

### GET: /api/auth/authenticate
- Get user data
- Requires authentication 
- Returns a `UserDto` object

### GET: /api/auth/log-out
- Log out by removing HTTP-only cookies

### PUT: /api/auth/update-email
- Updates the user’s email
- Requires authentication

### PUT: /api/auth/forgot-password
- Sends a password reset link to the user’s email

### PUT: /api/auth/reset-password
```json
{
  "token": "reset-token",
  "password": "12345678"
}
```
- Resets the user's password using a token

### PUT: /api/auth/update-password
```json
{
  "currentPassword": "12345678",
  "newPassword": "newPassword123"
}
```
- Changes the user’s current password
- Requires authentication

### GET: /api/users
- Get list of users

### GET: /api/users/csv
- Download list of users in csv file

### GET: /api/users/:id
  - Get chosen user by id

### POST: /api/files/upload
- multipart/form-data, *file* key
- Upload file

### GET: /api/files/:name
- Download chosen file by name

### DELETE: /api/files/:name
- Delete chosen file by name

## Run with Docker
1. Set up environment variables in `.env` file (check `.env.example`)
2. Build app
```shell
mvn clean package
```
3. Build Docker image
```shell
docker build -t spring-boot-starter .
```
4. Create container with Docker Compose
```shell
docker-compose --env-file .env up --build
```