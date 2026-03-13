package com.repository;

import com.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(@Email(message = "email is not in format") String email);

    Optional<User> findByEmail(String email);
}
