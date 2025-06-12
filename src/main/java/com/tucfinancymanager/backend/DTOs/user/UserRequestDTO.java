package com.tucfinancymanager.backend.DTOs.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Foto do Usuario")
    private String userPhoto;

    @NotEmpty()
    @Schema(example = "Thiago", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do Usuario")
    @Length(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotEmpty()
    @Email(message = "Insira um email valido")
    @Schema(example = "example@mail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email do Usuario")
    private String email;

    @NotEmpty()
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Telefone deve estar no formato (11) 99999-9999")
    @Schema(example = "(11) 99999-999", requiredMode = Schema.RequiredMode.REQUIRED, description = "Telefone do Usuario")
    private String phone;

    @NotEmpty()
    @Schema(example = "3500.00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Renda mensal do usuário" )
    @PositiveOrZero(message = "A renda mensal não pode ser negativa")
    private Double monthlyIncome;


    @NotEmpty()
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A senha deve conter no mínimo 8 caracteres, com pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial"

    )
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do Usuario")
    private String password;

}
