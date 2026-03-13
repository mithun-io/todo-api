package com.controller;

import com.dto.ApiResponse;
import com.dto.UserRequest;
import com.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRequest>> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("registered successfully", userService.register(userRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("login successful", userService.login(userRequest.getEmail(), userRequest.getPassword())));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader(required = false) String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("logout successful", userService.logout(sessionId)));
    }
}