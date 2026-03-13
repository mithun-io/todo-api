package com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @Size(min = 3, max = 15, message = "username is required")
    private String name;

    @Email(message = "email is not in format")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\d)(?=.*[!@#$%&*:])[A-Za-z0-9\\d!@#$%&*:]{8,}$", message = "password should contain of uppercase, lowercase, numbers, special characters")
    private String password;
}
