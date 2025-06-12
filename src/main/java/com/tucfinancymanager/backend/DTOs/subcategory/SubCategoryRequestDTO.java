package com.tucfinancymanager.backend.DTOs.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequestDTO {

    @NotNull(message = "O id da categoria é obrigatório")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da Categoria")
    private UUID categoryId;

    @NotBlank(message = "O nome da subcategoria é obrigatório")
    @Schema(example = "Restaurante", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da subcategoria")
    private String subcategoryName;
}
