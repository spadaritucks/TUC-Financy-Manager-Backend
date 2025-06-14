package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
}
