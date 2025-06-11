package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "future_transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FutureTransaction {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subcategoryId")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da subcategoria")
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    @Schema(example = "Monthly ",requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo de Transação")
    private TransactionTypeEnum transactionType;

    @Schema(example = "250.75", requiredMode = Schema.RequiredMode.REQUIRED, description = "Valor monetário da transação")
    private Double transactionValue;

    @Schema(example = "Pagamento da fatura do cartão", description = "Descrição adicional da transação")
    private String description;

    @Schema(example = "true", description = "Indica se a transação é recorrente (mensal, semanal, etc.)")
    private Boolean recurrent;

    @Enumerated(EnumType.STRING)
    @Schema(example = "PENDING", description = "Status da transação: PENDING, COMPLETED etc.")
    private TransactionStatusEnum transactionStatus;

    @Enumerated(EnumType.STRING)
    @Schema(example = "MONTHLY", description = "Frequência da recorrência, se aplicável: DAILY, WEEKLY, MONTHLY, YEARLY")
    private TransactionRecurrenceFrequencyEnum recurrenceFrequency;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


}
