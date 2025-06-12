package com.tucfinancymanager.backend.DTOs.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @Schema(
            example = "https://example.com/profile.jpg",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Foto do Usuário (URL opcional)"
    )
    private String userPhoto;

    @NotBlank(message = "O nome é obrigatório")
    @Length(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Schema(
            example = "Thiago",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Nome do Usuário"
    )
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Insira um e-mail válido")
    @Schema(
            example = "example@mail.com",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "E-mail do Usuário"
    )
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(
            regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}",
            message = "Telefone deve estar no formato (11) 99999-9999"
    )
    @Schema(
            example = "(11) 99999-9999",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Telefone do Usuário"
    )
    private String phone;

    @NotNull(message = "A renda mensal é obrigatória")
    @PositiveOrZero(message = "A renda mensal não pode ser negativa")
    @Schema(
            example = "3500.00",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Renda mensal do Usuário"
    )
    private Double monthlyIncome;

    @NotBlank(message = "A senha é obrigatória")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, com ao menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
    )
    @Schema(
            example = "Senha@123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Senha do Usuário"
    )
    private String password;
}
