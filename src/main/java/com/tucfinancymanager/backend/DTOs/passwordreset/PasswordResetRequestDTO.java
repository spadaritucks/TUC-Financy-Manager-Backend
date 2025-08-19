package com.tucfinancymanager.backend.DTOs.passwordreset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Insira um e-mail válido")
    @Schema(
            example = "example@mail.com",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "E-mail do usuário para envio do código de reset"
    )
    private String email;
}
