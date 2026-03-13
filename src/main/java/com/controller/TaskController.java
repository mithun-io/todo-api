package com.controller;

import com.dto.ApiResponse;
import com.dto.TaskRequest;
import com.dto.TaskResponse;
import com.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> insert(@RequestBody @Valid TaskRequest taskRequest, @RequestHeader(required = false) String sessionId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("task added successfully", taskService.insert(taskRequest, sessionId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> fetchAll(@RequestHeader(required = false) String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("tasks fetched successfully", taskService.fetchAll(sessionId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> fetchById(@RequestHeader(required = false) String sessionId, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("task fetched successfully", taskService.fetchById(id, sessionId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest, @RequestHeader(required = false) String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("task updated successfully", taskService.update(id, taskRequest, sessionId)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> patch(@PathVariable Long id, @RequestBody TaskRequest taskRequest, @RequestHeader(required = false) String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("task patched successfully", taskService.patch(id, taskRequest, sessionId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id, @RequestHeader(required = false) String sessionId) {
        taskService.delete(id, sessionId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("task deleted successfully", "deleted"));
    }
}