package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.TransactionStatusEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    private Double transactionValue;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum transactionStatus;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
