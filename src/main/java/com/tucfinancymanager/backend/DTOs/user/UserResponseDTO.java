package com.tucfinancymanager.backend.DTOs.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    @Schema(description = "Identificador do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "URL da foto do usuário", example = "https://exemplo.com/foto.jpg")
    private String userPhoto;

    @Schema(description = "Nome do usuário", example = "Thiago Henrique")
    private String name;

    @Schema(description = "E-mail do usuário", example = "thiago@email.com")
    private String email;

    @Schema(description = "Telefone do usuário", example = "(11) 99999-9999")
    private String phone;

    @Schema(description = "Renda mensal do usuário", example = "5000.00")
    private Double monthlyIncome;

    @Schema(description = "Registro de quanto a criação foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp created_at;

    @Schema(description = "Registro de quanto a atualização foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp updated_at;
}
