package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import com.tucfinancymanager.backend.ENUMs.GoalType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @Column(nullable = false)
    private Double targetValue;


    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
