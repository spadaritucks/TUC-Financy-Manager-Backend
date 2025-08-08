package com.tucfinancymanager.backend.DTOs.transaction;

import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    @Schema(description = "Identificador do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;


    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Id do usuário")
    private UUID userId;


    @Schema(example = "234e5678-e89b-12d3-a456-426614174000", description = "Id da subcategoria")
    private UUID subCategoryId;


    @Schema(example = "INCOME", description = "Tipo de Transação. Valores possíveis: INCOME, EXPENSE")
    private TransactionTypeEnum transactionType;


    @Schema(example = "250.75", description = "Valor da transação")
    private Double transactionValue;


    @Schema(example = "Pagamento da fatura do cartão", description = "Descrição da transação")
    private String description;


    @Schema(example = "2025-01-01T00:00:00", description = "Data da transação (formato: yyyy-MM-dd'T'HH:mm:ss)")
    private LocalDate transactionDate;

    @Schema(example = "true", requiredMode = Schema.RequiredMode.REQUIRED, description = "Indica se a transação é recorrente")
    private Boolean recurrent;

    @Schema(example = "PENDING", description = "Status da transação. Valores possíveis: PENDING, COMPLETED, CANCELED")
    private TransactionStatusEnum transactionStatus;

    @Schema(example = "MONTHLY", requiredMode = Schema.RequiredMode.REQUIRED, description = "Frequência da recorrência. Valores: DAILY, WEEKLY, MONTHLY, YEARLY")
    private TransactionRecurrenceFrequencyEnum recurrenceFrequency;

    @Schema(description = "Registro de quanto a criação foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp created_at;

    @Schema(description = "Registro de quanto a atualização foi feita", example = "2025-06-16T14:21:00.914+00:00")
    private Timestamp updated_at;
}
