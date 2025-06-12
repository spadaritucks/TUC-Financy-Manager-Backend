package com.tucfinancymanager.backend.DTOs.futureTransactions;

import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FutureTransactionRequestDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    @Schema(example = "f47ac10b-58cc-4372-a567-0e02b2c3d479", requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuário")
    private UUID userId;

    @NotNull(message = "O id da subcategoria é obrigatório")
    @Schema(example = "c9bf9e57-1685-4c89-bafb-ff5af830be8a", requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da subcategoria")
    private UUID subCategoryId;

    @NotNull(message = "O tipo da transação é obrigatório")
    @Schema(example = "INCOME", requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo de Transação. Valores: INCOME, EXPENSE")
    private TransactionTypeEnum transactionType;

    @NotNull(message = "O valor da transação é obrigatório")
    @Positive(message = "O valor da transação deve ser positivo")
    @Schema(example = "250.75", requiredMode = Schema.RequiredMode.REQUIRED, description = "Valor da transação")
    private Double transactionValue;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Pattern(regexp = "^$|^(?!\\s*$).+", message = "A descrição não pode ser vazia ou apenas espaços")
    @Schema(example = "Pagamento da fatura do cartão", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Descrição da transação")
    private String description;

    @NotNull(message = "O campo de recorrência é obrigatório")
    @Schema(example = "true", requiredMode = Schema.RequiredMode.REQUIRED, description = "Indica se a transação é recorrente")
    private Boolean recurrent;

    @NotNull(message = "O status da transação é obrigatório")
    @Schema(example = "PENDING", requiredMode = Schema.RequiredMode.REQUIRED, description = "Status da transação. Valores: PENDING, COMPLETED, CANCELED")
    private TransactionStatusEnum transactionStatus;

    @NotNull(message = "A frequência da recorrência é obrigatória")
    @Schema(example = "MONTHLY", requiredMode = Schema.RequiredMode.REQUIRED, description = "Frequência da recorrência. Valores: DAILY, WEEKLY, MONTHLY, YEARLY")
    private TransactionRecurrenceFrequencyEnum recurrenceFrequency;
}
