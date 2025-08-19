package com.tucfinancymanager.backend.DTOs.passwordreset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Insira um e-mail válido")
    @Schema(
            example = "example@mail.com",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "E-mail do usuário"
    )
    private String email;

    @NotBlank(message = "O código é obrigatório")
    @Pattern(regexp = "^[0-9]{6}$", message = "O código deve conter exatamente 6 dígitos")
    @Schema(
            example = "123456",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Código de verificação de 6 dígitos"
    )
    private String code;

    @NotBlank(message = "A nova senha é obrigatória")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, com ao menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
    )
    @Schema(
            example = "NovaSenha@123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nova senha do usuário"
    )
    private String newPassword;
}
