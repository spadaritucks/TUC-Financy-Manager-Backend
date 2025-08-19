package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    
    @Query("SELECT p FROM password_reset_tokens p WHERE p.email = :email AND p.code = :code AND p.used = false AND p.expiresAt > :now")
    Optional<PasswordResetToken> findValidToken(@Param("email") String email, @Param("code") String code, @Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM password_reset_tokens p WHERE p.email = :email AND p.used = false AND p.expiresAt > :now")
    Optional<PasswordResetToken> findValidTokenByEmail(@Param("email") String email, @Param("now") LocalDateTime now);
}
