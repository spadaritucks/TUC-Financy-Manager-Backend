package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
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

@Entity(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty()
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuario")
    private User user;

    @NotEmpty()
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da subcategoria")
    private SubCategory subCategory;

    @NotNull()
    @Enumerated(EnumType.STRING)
    @Schema(example = "Monthly ",requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo de Transação")
    private TransactionTypeEnum transactionType;

    @NotEmpty()
    @Schema(example = "250.75", requiredMode = Schema.RequiredMode.REQUIRED, description = "Valor monetário da transação")
    private Double transactionValue;

    @NotEmpty()
    @Schema(example = "Pagamento da fatura do cartão", description = "Descrição adicional da transação")
    private String description;

    @NotNull()
    @Enumerated(EnumType.STRING)
    @Schema(example = "PENDING", description = "Status da transação: PENDING, COMPLETED etc.")
    private TransactionStatusEnum transactionStatus;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
