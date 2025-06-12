package com.tucfinancymanager.backend.DTOs.subcategory;

import com.tucfinancymanager.backend.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequestDTO {

    @NotEmpty()
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da Categoria")
    private UUID categoryId;

    @NotEmpty()
    @Schema(example = "Restaurante", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da subcategoria")
    private String subcategoryName;
}
