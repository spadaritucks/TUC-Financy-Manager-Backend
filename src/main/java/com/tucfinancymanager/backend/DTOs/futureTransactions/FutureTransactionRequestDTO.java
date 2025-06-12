package com.tucfinancymanager.backend.DTOs.futureTransactions;

import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FutureTransactionRequestDTO {

    @Schema(description = "Id do usuário", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O id do usuário é obrigatório")
    private UUID userId;

    @Schema(description = "Id da subcategoria", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O id da subcategoria é obrigatório")
    private UUID subCategoryId;

    @Schema(description = "Tipo de Transação. Valores: INCOME, EXPENSE", example = "INCOME", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O tipo da transação é obrigatório")
    private TransactionTypeEnum transactionType;

    @Schema(description = "Valor da transação", example = "250.75", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O valor da transação é obrigatório")
    @Positive(message = "O valor da transação deve ser positivo")
    private Double transactionValue;

    @Schema(description = "Descrição da transação", example = "Pagamento da fatura do cartão", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String description;

    @Schema(description = "Indica se a transação é recorrente", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O campo de recorrência é obrigatório")
    private Boolean recurrent;

    @Schema(description = "Status da transação. Valores: PENDING, COMPLETED, CANCELED", example = "PENDING", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O status da transação é obrigatório")
    private TransactionStatusEnum transactionStatus;

    @Schema(description = "Frequência da recorrência. Valores: DAILY, WEEKLY, MONTHLY, YEARLY", example = "MONTHLY", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A frequência da recorrência é obrigatória")
    private TransactionRecurrenceFrequencyEnum recurrenceFrequency;
}
