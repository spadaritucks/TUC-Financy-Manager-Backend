package com.tucfinancymanager.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.tucfinancymanager.backend.DTOs.auth.AuthRequestDTO;
import com.tucfinancymanager.backend.DTOs.auth.AuthResponseDTO;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.AuthorizationException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    private User user;
    private AuthRequestDTO authRequestDTO;
    private String token;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.fake.token";

        user = new User();
        user.setId(userId);
        user.setUserPhoto("https://example.com/photo.jpg");
        user.setName("João Silva");
        user.setEmail("joao@email.com");
        user.setPhone("(11) 99999-9999");
        user.setMonthlyIncome(5000.0);
        user.setPassword("$2a$10$hashedPassword"); // Senha já criptografada
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));

        // Criando dados de entrada para o teste
        authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setEmail("joao@email.com");
        authRequestDTO.setPassword("senhaOriginal123"); // Senha em texto plano
    }

    @Test
    @DisplayName("should be able return user data and user token")
    void shouldBeAbleReturnUserDataAnduserToken() {

        when(usersRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senhaOriginal123", "$2a$10$hashedPassword")).thenReturn(true);

        when(tokenService.generateToken(user)).thenReturn(token);

        AuthResponseDTO result = authService.auth(authRequestDTO);

        assertNotNull(result);
        assertEquals(token, result.getToken());
        assertNotNull(result.getUser());

    }

    @Test
    @DisplayName("should be able throw not found exception when user already exists")
    void shouldBeAbleThrowNotFoundExceptionWhenUserAlreadyExists () {

        when(usersRepository.findByEmail("incorrect@email.com")).thenReturn(Optional.empty());
        authRequestDTO.setEmail("incorrect@email.com");

        NotFoundException exception = assertThrows(NotFoundException.class, 
        () -> authService.auth(authRequestDTO));

        assertNotNull(exception);
        assertEquals("Usuario não existe", exception.getMessage());
        
    }

    @Test
    @DisplayName("should be able throw authorization exception when password is incorrect")
    void shouldBeAbleThrowAuthorizationExceptionWhenPasswordIsIncorrect () {

        when(usersRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(user));

        authRequestDTO.setEmail(user.getEmail());

        when(passwordEncoder.matches("senhaErrada123", "$2a$10$hashedPassword"))
        .thenReturn(false);

        authRequestDTO.setPassword("senhaErrada123");

        AuthorizationException exception = assertThrows(AuthorizationException.class, () -> authService.auth(authRequestDTO));
        assertNotNull(exception);
        assertEquals("Senha incorreta", exception.getMessage());
    }

}
