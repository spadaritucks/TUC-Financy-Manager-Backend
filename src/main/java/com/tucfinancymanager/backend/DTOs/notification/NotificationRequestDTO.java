package com.tucfinancymanager.backend.DTOs.notification;

import com.tucfinancymanager.backend.ENUMs.NotificationTypeEnum;
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
public class NotificationRequestDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    @Schema(
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Usuário que receberá a notificação"
    )
    private UUID userId;

    @NotNull(message = "O tipo da notificação é obrigatório")
    @Schema(
            example = "TRANSACTION",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Tipo da Notificação. Valores possíveis: TRANSACTION, GOAL, FUTURE_TRANSACTION"
    )
    private NotificationTypeEnum notificationType;

    @Schema(
            example = "111e2222-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Transação relacionada à notificação (se aplicável)"
    )
    private UUID transactionId;

    @Schema(
            example = "222e3333-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Transação futura relacionada à notificação (se aplicável)"
    )
    private UUID futureTransactionId;

    @Schema(
            example = "333e4444-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Meta relacionada à notificação (se aplicável)"
    )
    private UUID goalId;

    @NotBlank(message = "A mensagem da notificação é obrigatória")
    @Schema(
            example = "Falta 1 dia para o pagamento",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Mensagem da Notificação"
    )
    private String message;

    @NotNull(message = "O status da notificação é obrigatório")
    @Schema(
            example = "false",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Verificação se a mensagem foi lida ou não"
    )
    private Boolean notificationStatus;
}
