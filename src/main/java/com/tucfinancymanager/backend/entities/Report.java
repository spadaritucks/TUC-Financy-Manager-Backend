package com.tucfinancymanager.backend.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Usuario solicitante do relatorio")
    private User user;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação relacionada ao relatorio (se aplicável)")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "future_transaction_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transação futura relacionada ao relatorio (se aplicável)")
    private FutureTransaction futureTransaction;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Meta relacionada ao relatorio (se aplicável)")
    private Goal goal;

    @NotEmpty()
    @Schema(requiredMode= Schema.RequiredMode.REQUIRED, description = "Data inicial da relatorio")
    private Timestamp startDate;

    @NotEmpty()
    @Schema(requiredMode= Schema.RequiredMode.REQUIRED, description = "Data final da relatorio")
    private Timestamp endDate;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
