package com.tucfinancymanager.backend.DTOs.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestUpdateDTO {


    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    @Schema(example = "Alimentação", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Nome da Categoria")
    private String categoryName;
}
