package com.tucfinancymanager.backend.DTOs.transaction;

import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    @Schema(
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Id do usuário"
    )
    private UUID userId;

    @NotNull(message = "O id da subcategoria é obrigatório")
    @Schema(
            example = "234e5678-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Id da subcategoria"
    )
    private UUID subCategoryId;

    @NotNull(message = "O tipo da transação é obrigatório")
    @Schema(
            example = "INCOME",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Tipo de Transação. Valores possíveis: INCOME, EXPENSE"
    )
    private TransactionTypeEnum transactionType;

    @NotNull(message = "O valor da transação é obrigatório")
    @Positive(message = "O valor da transação deve ser positivo")
    @Schema(
            example = "250.75",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Valor da transação"
    )
    private Double transactionValue;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Pattern(regexp = "^$|^(?!\\s*$).+", message = "A descrição não pode ser vazia ou apenas espaços")
    @Schema(
            example = "Pagamento da fatura do cartão",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Descrição da transação"
    )
    private String description;

    @NotNull(message = "O status da transação é obrigatório")
    @Schema(
            example = "PENDING",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Status da transação. Valores possíveis: PENDING, COMPLETED, CANCELED"
    )
    private TransactionStatusEnum transactionStatus;
}
