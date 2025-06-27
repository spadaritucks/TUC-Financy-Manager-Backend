package com.tucfinancymanager.backend.DTOs.goal;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalResponseDTO {

    @Schema(description = "Identificador do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;


    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuário")
    private UUID userId;

    @Schema(example = "9a7b6c5d-4e3f-2a1b-0c9d-8e7f6a5b4c3d", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Id da subcategoria")
    private UUID subCategoryId;



    @Schema(example = "Juntar 100 reais por mês", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da Meta")
    private String goalName;



    @Schema(example = "2000.00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Valor da meta")
    private Double targetValue;


    @Schema(example = "2025-01-01T00:00:00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Data inicial da meta (formato: yyyy-MM-dd'T'HH:mm:ss)")
    private LocalDateTime startDate;


    @Schema(example = "2025-12-31T00:00:00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Data final da meta (formato: yyyy-MM-dd'T'HH:mm:ss)")
    private LocalDateTime endDate;


    @Schema(example = "IN_PROGRESS", requiredMode = Schema.RequiredMode.REQUIRED, description = "Status da meta. Valores possíveis: IN_PROGRESS, COMPLETED, CANCELED")
    private GoalStatus goalStatus;

    @Schema(description = "Registro de quanto a criação foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp created_at;

    @Schema(description = "Registro de quanto a atualização foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp updated_at;
}
