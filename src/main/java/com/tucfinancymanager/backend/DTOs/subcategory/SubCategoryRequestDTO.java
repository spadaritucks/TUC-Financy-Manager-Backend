package com.tucfinancymanager.backend.DTOs.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequestDTO {

    @NotNull(message = "O id da categoria é obrigatório")
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000",requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da Categoria")
    private UUID categoryId;

    @NotBlank(message = "O nome da subcategoria é obrigatório")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    @Schema(example = "Restaurante", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da subcategoria")
    private String subcategoryName;
}
