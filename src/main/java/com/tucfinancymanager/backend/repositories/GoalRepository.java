package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.Goal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
    Optional<Goal> findBygoalName(String goalName);
    List<Goal> findByuserId (UUID id, Pageable pageable);

    @Query("SELECT g FROM goals g WHERE g.endDate < :today")
    List<Goal> findExpiredEndDate(LocalDateTime today);
}
