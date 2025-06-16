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
public class UserRequestUpdateDTO {

    @Schema(
            example = "https://example.com/profile.jpg",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Foto do Usuário (URL opcional)"
    )
    private String userPhoto;


    @Length(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Schema(
            example = "Thiago",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Nome do Usuário"
    )
    private String name;


    @Email(message = "Insira um e-mail válido")
    @Schema(
            example = "example@mail.com",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "E-mail do Usuário"
    )
    private String email;


    @Pattern(
            regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}",
            message = "Telefone deve estar no formato (11) 99999-9999"
    )
    @Schema(
            example = "(11) 99999-9999",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Telefone do Usuário"
    )
    private String phone;

    @PositiveOrZero(message = "A renda mensal não pode ser negativa")
    @Schema(
            example = "3500.00",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Renda mensal do Usuário"
    )
    private Double monthlyIncome;


}
