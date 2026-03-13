package com.mapper;

import com.dto.TaskRequest;
import com.dto.TaskResponse;
import com.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "taskName", source = "name")
    Task toTask(TaskRequest taskRequest);

    @Mapping(target = "name", source = "taskName")
    TaskRequest toTaskRequest(Task task);

    @Mapping(target = "name", source = "taskName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "taskStatus")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "modifiedAt", source = "modifiedAt")
    TaskResponse toTaskResponse(Task task);
}
