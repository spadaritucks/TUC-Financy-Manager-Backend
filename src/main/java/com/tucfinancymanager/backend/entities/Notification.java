package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.NotificationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Usuário que receberá a notificação")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(example = "TRANSACTION", requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo da Notificação")
    private NotificationTypeEnum notificationType;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação relacionada à notificação (se aplicável)")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "future_transaction_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação futura relacionada à notificação (se aplicável)")
    private FutureTransaction futureTransaction;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Meta relacionada à notificação (se aplicável)")
    private Goal goal;

    @NotNull
    @Schema(example = "Falta 1 dia para o pagamento", requiredMode = Schema.RequiredMode.REQUIRED, description = "Mensagem da Notificação")
    private String message;

    @Schema(example = "true",requiredMode = Schema.RequiredMode.REQUIRED, description = "Verficiação se a mensagem foi lida ou não")
    private Boolean notificationStatus;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
