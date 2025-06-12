package com.tucfinancymanager.backend.DTOs.report;

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
public class ReportRequestDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Usuário solicitante do relatório")
    private UUID userId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação relacionada ao relatório (se aplicável)")
    private UUID transactionId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação futura relacionada ao relatório (se aplicável)")
    private UUID futureTransactionId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Meta relacionada ao relatório (se aplicável)")
    private UUID goalId;

    @NotBlank(message = "A data inicial é obrigatória")
    @Schema(
            example = "2025-01-01",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Data inicial do relatório (formato: yyyy-MM-dd)"
    )
    private String startDate;

    @NotBlank(message = "A data final é obrigatória")
    @Schema(
            example = "2025-06-30",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Data final do relatório (formato: yyyy-MM-dd)"
    )
    private String endDate;
}

