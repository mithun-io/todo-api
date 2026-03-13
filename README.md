### 1. Register a new user
POST http://localhost:8080/api/register
Content-Type: application/json

{
  "name": "user",
  "email": "user@example.com",
  "password": "user"
}

### 2. Login to get Session ID
# This will return a sessionId in the "data" field of the response body.
POST http://localhost:8080/api/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "user"
}


# ========================================================
# IMPORTANT: Put your Session ID from above here!
# Replace 'YOUR-SESSION-ID-HERE' with the real string from Login
# ========================================================
@session_id = YOUR-SESSION-ID-HERE


### 3. POST a new Task
POST http://localhost:8080/api/tasks
Content-Type: application/json
sessionId: {{session_id}}

{
  "name": "My First Task",
  "description": "I need to configure this API",
  "status": "PENDING"
}

### 4. GET all Tasks
GET http://localhost:8080/api/tasks
sessionId: {{session_id}}

### 5. PUT (Fully Update) a Task
# Assuming the Task ID created above was "1" (Change the URL if different)
PUT http://localhost:8080/api/tasks/1
Content-Type: application/json
sessionId: {{session_id}}

{
  "name": "My Updated Task",
  "description": "I completely replaced the payload using PUT",
  "status": "COMPLETED"
}

### 6. PATCH (Partially Update) a Task
# Just changing the status back to PENDING
PATCH http://localhost:8080/api/tasks/1
Content-Type: application/json
sessionId: {{session_id}}

{
  "status": "PENDING"
}

### 7. DELETE a Task
DELETE http://localhost:8080/api/tasks/1
sessionId: {{session_id}}

### 8. Logout and terminate Session ID
POST http://localhost:8080/api/logout
sessionId: {{session_id}}
