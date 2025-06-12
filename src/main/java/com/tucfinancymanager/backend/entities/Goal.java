package com.tucfinancymanager.backend.entities;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import jakarta.persistence.*;
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;


    private String goalName;


    private Double targetValue;


    private Timestamp startDate;


    private Timestamp endDate;


    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
