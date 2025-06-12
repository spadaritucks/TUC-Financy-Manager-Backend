package com.tucfinancymanager.backend.DTOs.notification;

import com.tucfinancymanager.backend.ENUMs.NotificationTypeEnum;
import com.tucfinancymanager.backend.entities.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Usuário que receberá a notificação")
    private UUID userId;

    @NotNull
    @Schema(example = "TRANSACTION", requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo da Notificação")
    private NotificationTypeEnum notificationType;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação relacionada à notificação (se aplicável)")
    private UUID transactionId;


    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação futura relacionada à notificação (se aplicável)")
    private UUID futureTransactionId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Meta relacionada à notificação (se aplicável)")
    private UUID goalId;

    @NotEmpty
    @Schema(example = "Falta 1 dia para o pagamento", requiredMode = Schema.RequiredMode.REQUIRED, description = "Mensagem da Notificação")
    private String message;

    @NotNull
    @Schema(example = "true",requiredMode = Schema.RequiredMode.REQUIRED, description = "Verficiação se a mensagem foi lida ou não")
    private Boolean notificationStatus;
}
