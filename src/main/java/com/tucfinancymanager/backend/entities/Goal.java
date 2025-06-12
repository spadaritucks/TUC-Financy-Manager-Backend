package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
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

@Entity(name = "goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id do usuario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subcategoryId")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Id da subcategoria")
    private SubCategory subCategory;

    @NotEmpty()
    @Schema(example = "Juntar 100 reais por mês", requiredMode= Schema.RequiredMode.REQUIRED, description = "Nome da Meta")
    private String goalName;

    @NotEmpty()
    @Schema(example = "2000.00", requiredMode= Schema.RequiredMode.REQUIRED, description = "Valor da meta")
    private Double targetValue;

    @NotEmpty()
    @Schema(requiredMode= Schema.RequiredMode.REQUIRED, description = "Data inicial da meta")
    private Timestamp startDate;

    @NotEmpty()
    @Schema(requiredMode= Schema.RequiredMode.REQUIRED, description = "Data final da meta")
    private Timestamp endDate;

    @NotEmpty()
    @Enumerated(EnumType.STRING)
    @Schema(example = "Monthly ",requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo de Transação")
    private GoalStatus goalStatus;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
