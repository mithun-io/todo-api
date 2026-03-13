package com.dto;

import com.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "task name required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "status is required")
    private TaskStatus status;
}
