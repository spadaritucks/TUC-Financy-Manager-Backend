package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.auth.AuthRequestDTO;
import com.tucfinancymanager.backend.DTOs.auth.AuthResponseDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.exceptions.AuthorizationException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public AuthResponseDTO auth (AuthRequestDTO authRequestDTO) {

        var user = this.usersRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow(
                () -> new NotFoundException("Usuario não existe")
        );

        var password = passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword());
        if(!password){
            throw new AuthorizationException("Senha incorreta");
        }

        String token = this.tokenService.generateToken(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getUserPhoto(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getMonthlyIncome(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        return new AuthResponseDTO(
                token,
                userResponseDTO
        );

    }
}
