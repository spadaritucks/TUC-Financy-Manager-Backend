package com.tucfinancymanager.backend.DTOs.report;

import com.tucfinancymanager.backend.entities.FutureTransaction;
import com.tucfinancymanager.backend.entities.Goal;
import com.tucfinancymanager.backend.entities.Transaction;
import com.tucfinancymanager.backend.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Usuario solicitante do relatorio")
    private UUID userId;


    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação relacionada ao relatorio (se aplicável)")
    private Transaction transaction;


    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação futura relacionada ao relatorio (se aplicável)")
    private UUID futureTransactionId;


    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Meta relacionada ao relatorio (se aplicável)")
    private UUID goalId;

    @NotEmpty()
    @Schema(requiredMode= Schema.RequiredMode.REQUIRED, description = "Data inicial da relatorio")
    private String startDate;

    @NotEmpty()
    @Schema(requiredMode= Schema.RequiredMode.REQUIRED, description = "Data final da relatorio")
    private String endDate;
}
