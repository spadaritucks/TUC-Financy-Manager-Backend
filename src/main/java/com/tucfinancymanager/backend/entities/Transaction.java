package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subCategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    @Column(nullable = false)
    private Double transactionValue;

    private String description;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private Boolean recurrent;

  
    @Enumerated(EnumType.STRING)
    private TransactionRecurrenceFrequencyEnum recurrenceFrequency;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
