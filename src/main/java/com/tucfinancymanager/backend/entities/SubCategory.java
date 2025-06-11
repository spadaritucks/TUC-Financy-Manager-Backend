package com.tucfinancymanager.backend.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "subcategories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id da Categoria")
    private Category category;

    @Schema(example = "Restaurante", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da subcategoria")
    private String subcategoryName;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
