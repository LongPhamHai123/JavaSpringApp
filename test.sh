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
  -H "Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTc3MTgyMDE3NCwiaWF0IjoxNzcxODE5ODc0fQ.e4gzFzgU3xvSRIzyTg0GCbVv5kTdotybnUiuizetXtY2EzUx8TxKoH8RjfdkyHcr"

curl -X GET http://localhost:8080/api/public/hello

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser1","password":"password123"}'