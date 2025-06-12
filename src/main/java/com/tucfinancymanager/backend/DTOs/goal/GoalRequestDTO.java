package com.tucfinancymanager.backend.DTOs.goal;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalRequestDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuário")
    @NotNull(message = "O id do usuário é obrigatório")
    private UUID userId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Id da subcategoria")
    private UUID subCategoryId;

    @Schema(example = "Juntar 100 reais por mês", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da Meta")
    @NotNull(message = "O nome da meta é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da meta deve ter entre 3 e 100 caracteres")
    private String goalName;

    @Schema(example = "2000.00", requiredMode = Schema.RequiredMode.REQUIRED, description = "Valor da meta")
    @NotNull(message = "O valor da meta é obrigatório")
    @Positive(message = "O valor da meta deve ser maior que zero")
    private Double targetValue;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Data inicial da meta")
    @NotNull(message = "A data de início é obrigatória")
    private Timestamp startDate;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Data final da meta")
    @NotNull(message = "A data de término é obrigatória")
    private Timestamp endDate;

    @Schema(example = "IN_PROGRESS", requiredMode = Schema.RequiredMode.REQUIRED, description = "Status da meta")
    @NotNull(message = "O status da meta é obrigatório")
    private GoalStatus goalStatus;
}
