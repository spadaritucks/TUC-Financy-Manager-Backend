package com.tucfinancymanager.backend.DTOs.goal;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalRequestDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuário")
    private UUID userId;

    @Schema(example = "9a7b6c5d-4e3f-2a1b-0c9d-8e7f6a5b4c3d", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Id da subcategoria")
    private UUID subCategoryId;

    @NotBlank(message = "O nome da meta é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da meta deve ter entre 3 e 100 caracteres")
    @Schema(example = "Juntar 100 reais por mês", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da Meta")
    private String goalName;

    @NotNull(message = "O valor da meta é obrigatório")
    @Positive(message = "O valor da meta deve ser maior que zero")
    @Schema(example = "2000.00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Valor da meta")
    private Double targetValue;

    @NotNull(message = "A data de início é obrigatória")
    @Schema(example = "2025-01-01T00:00:00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Data inicial da meta (formato: yyyy-MM-dd'T'HH:mm:ss)")
    private LocalDateTime startDate;

    @NotNull(message = "A data de término é obrigatória")
    @Schema(example = "2025-12-31T00:00:00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Data final da meta (formato: yyyy-MM-dd'T'HH:mm:ss)")
    private LocalDateTime endDate;

    @NotNull(message = "O status da meta é obrigatório")
    @Schema(example = "IN_PROGRESS", requiredMode = Schema.RequiredMode.REQUIRED, description = "Status da meta. Valores possíveis: IN_PROGRESS, COMPLETED, CANCELED")
    private GoalStatus goalStatus;
}
