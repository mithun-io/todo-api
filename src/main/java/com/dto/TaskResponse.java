package com.dto;

import com.enums.TaskStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponse {

    private String name;

    private String description;

    private TaskStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}