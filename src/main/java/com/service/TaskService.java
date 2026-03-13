package com.service;

import com.dto.TaskRequest;
import com.dto.TaskResponse;
import com.enums.SessionStatus;
import com.mapper.TaskMapper;
import com.model.Session;
import com.model.Task;
import com.repository.SessionRepository;
import com.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final SessionRepository sessionRepository;
    private final TaskMapper taskMapper;

    public boolean checkSession(String sessionID) {
        if (sessionID != null) {
            Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));
            return session.getSessionStatus() == SessionStatus.ACTIVE;
        }
        return false;
    }

    public TaskResponse insert(TaskRequest taskRequest, String sessionID) {
        if (!checkSession(sessionID)) throw new RuntimeException("invalid or expired session");
        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));
        if (session.getSessionStatus() != SessionStatus.ACTIVE)
            throw new RuntimeException("invalid or expired session");

        Task task = taskMapper.toTask(taskRequest);
        task.setUser(session.getUser());
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }

    public List<TaskResponse> fetchAll(String sessionID) {
        if (!checkSession(sessionID)) throw new RuntimeException("invalid or expired session");

        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));

        return taskRepository.findAllByUser(session.getUser()).stream().map(taskMapper::toTaskResponse).collect(Collectors.toList());
    }

    public TaskResponse fetchById(Long taskId, String sessionID) {
        if (!checkSession(sessionID)) throw new RuntimeException("invalid or expired session");

        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));

        Task task = taskRepository.findByIdAndUser(taskId, session.getUser()).orElseThrow(() -> new NoSuchElementException("task not found"));

        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse update(Long taskId, TaskRequest taskRequest, String sessionID) {
        if (!checkSession(sessionID)) throw new RuntimeException("invalid or expired session");

        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));

        Task task = taskRepository.findByIdAndUser(taskId, session.getUser()).orElseThrow(() -> new NoSuchElementException("task not found"));

        task.setTaskName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setTaskStatus(taskRequest.getStatus());

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(updatedTask);
    }

    public TaskResponse patch(Long taskId, TaskRequest taskRequest, String sessionID) {
        if (!checkSession(sessionID)) throw new RuntimeException("invalid or expired session");

        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));

        Task task = taskRepository.findByIdAndUser(taskId, session.getUser()).orElseThrow(() -> new NoSuchElementException("task not found"));

        if (taskRequest.getName() != null) task.setTaskName(taskRequest.getName());
        if (taskRequest.getDescription() != null) task.setDescription(taskRequest.getDescription());
        if (taskRequest.getStatus() != null) task.setTaskStatus(taskRequest.getStatus());

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(updatedTask);
    }

    public void delete(Long taskId, String sessionID) {
        if (!checkSession(sessionID)) throw new RuntimeException("invalid or expired session");

        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("no session found"));

        Task task = taskRepository.findByIdAndUser(taskId, session.getUser()).orElseThrow(() -> new NoSuchElementException("task not found"));

        taskRepository.delete(task);
    }
}