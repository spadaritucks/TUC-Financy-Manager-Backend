package com.tucfinancymanager.backend.DTOs.report;

import com.tucfinancymanager.backend.ENUMs.ReportTypeEnum;
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
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED, description = "Usuário solicitante do relatório")
    private UUID userId;

    @NotNull(message = "O tipo do relatório é obrigatório")
    @Schema(example = "TRANSACTION", requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo de Relatório. Valores possíveis: TRANSACTION, GOAL, FUTURE_TRANSACTION")
    private ReportTypeEnum reportType;

    @Schema(example = "7e57d004-2b97-0e7a-b45f-5387367791cd", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação relacionada ao relatório (se aplicável)")
    private UUID transactionId;

    @Schema(example = "9b2e6c47-4d5b-4a9c-bddf-1e2c95f7e167", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação futura relacionada ao relatório (se aplicável)")
    private UUID futureTransactionId;

    @Schema(example = "5a14c1d2-62f0-4130-b9a2-731e1d6f7d80", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Meta relacionada ao relatório (se aplicável)")
    private UUID goalId;

    @NotBlank(message = "A data inicial é obrigatória")
    @Schema(example = "2025-01-01", requiredMode = Schema.RequiredMode.REQUIRED, description = "Data inicial do relatório (formato: yyyy-MM-dd)")
    private String startDate;

    @NotBlank(message = "A data final é obrigatória")
    @Schema(example = "2025-06-30", requiredMode = Schema.RequiredMode.REQUIRED, description = "Data final do relatório (formato: yyyy-MM-dd)")
    private String endDate;
}
