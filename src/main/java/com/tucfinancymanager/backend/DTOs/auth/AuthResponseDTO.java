package com.tucfinancymanager.backend.DTOs.auth;


import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String token;
    private UserResponseDTO user;
}
