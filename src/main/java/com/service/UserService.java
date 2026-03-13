package com.service;

import com.dto.UserRequest;
import com.enums.SessionStatus;
import com.exception.UserExistsException;
import com.mapper.UserMapper;
import com.model.Session;
import com.model.User;
import com.repository.SessionRepository;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;

    public UserRequest register(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) throw new UserExistsException("user already exists");
        User user = userMapper.toUser(userRequest);
        User save = userRepository.save(user);
        return userMapper.toUserRequest(save);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("user not found"));
        if (!user.getPassword().equals(password)) throw new IllegalArgumentException("invalid credentials");

        String sessionID = UUID.randomUUID().toString();
        Session session = new Session();
        session.setSessionID(sessionID);
        session.setLoggedInTime(LocalDateTime.now());
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);

        sessionRepository.save(session);
        return sessionID;
    }

    public String logout(String sessionID) {
        if (sessionID == null) throw new IllegalArgumentException("invalid session");
        Session session = sessionRepository.findBySessionID(sessionID).orElseThrow(() -> new NoSuchElementException("session not found"));
        session.setSessionStatus(SessionStatus.EXPIRED);
        session.setLoggedOutTime(LocalDateTime.now());
        sessionRepository.save(session);
        return sessionID;
    }
}
