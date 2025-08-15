package com.tucfinancymanager.backend.DTOs.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {

    @Schema(description = "Identificador da categoria", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(example = "Alimentação", description = "Nome da Categoria")
    private String categoryName;

    @Schema(description = "Identificador do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "Registro de quanto a criação foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp created_at;

    @Schema(description = "Registro de quanto a atualização foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp updated_at;
}
