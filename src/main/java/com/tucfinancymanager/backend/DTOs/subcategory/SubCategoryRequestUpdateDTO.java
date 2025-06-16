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
public class SubCategoryRequestUpdateDTO {

    @Schema(example = "Restaurante", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Nome da subcategoria")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    private String subcategoryName;

}
