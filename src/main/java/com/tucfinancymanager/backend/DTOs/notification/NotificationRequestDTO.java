package com.tucfinancymanager.backend.DTOs.notification;

import com.tucfinancymanager.backend.ENUMs.NotificationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {



    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Usuário que receberá a notificação")
    private UUID userId;

    @Schema(example = "TRANSACTION", description = "Tipo da Notificação. Valores possíveis: TRANSACTION, GOAL, FUTURE_TRANSACTION")
    private NotificationTypeEnum notificationType;

    @Schema(example = "Transação registrada com sucesso", description = "Titulo da Notificação")
    private String title;

    @Schema(example = "Falta 1 dia para o pagamento", description = "Mensagem da Notificação")
    private String message;

}
