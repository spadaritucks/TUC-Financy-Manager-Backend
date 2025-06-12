package com.tucfinancymanager.backend.DTOs.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {

    @NotEmpty()
    @Schema(example = "Alimentação", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da Categoria")
    private String categoryName;
}
