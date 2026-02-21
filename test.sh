# Register a user
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123","email":"test@example.com"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'


# Access protected endpoint (include session cookie from login)
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTc3MTIzNjMzNiwiaWF0IjoxNzcxMjM2MDM2fQ.1v7BJjFTIn9l9mfrOWPo5Q2hOJNsQvKxih1aWURcJhF1gZhISFov4XD_rv2wROWB"