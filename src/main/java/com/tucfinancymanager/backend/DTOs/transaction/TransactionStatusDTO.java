package com.tucfinancymanager.backend.DTOs.transaction;

import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatusDTO {

    @NotNull(message = "O status da transação é obrigatório")
    @Schema(
            example = "PENDING",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Status da transação. Valores possíveis: PENDING, COMPLETED, CANCELED"
    )
    private TransactionStatusEnum transactionStatus;

}
