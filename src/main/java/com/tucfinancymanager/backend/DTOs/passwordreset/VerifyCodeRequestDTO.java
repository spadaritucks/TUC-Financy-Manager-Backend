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
public class VerifyCodeRequestDTO {

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
}
