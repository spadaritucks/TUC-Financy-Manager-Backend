package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import com.tucfinancymanager.backend.entities.Goal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
        Optional<Goal> findBygoalName(String goalName);

        @Query(value = """
                        SELECT g from goals g
                        JOIN FETCH g.subCategory s
                        JOIN FETCH s.category c
                        WHERE g.user.id = :userId
                        AND (:subcategory IS NULL OR s.subcategoryName ILIKE :subcategory )
                        AND (:goalName IS NULL OR g.goalName ILIKE :goalName )
                        AND (:goalStatus IS NULL OR g.goalStatus = :goalStatus)
                                        """)
        Page<Goal> findByuserId(
                        @Param("userId") UUID userId,
                        @Param("subcategory") String subcategory,
                        @Param("goalName") String goalName,
                        @Param("goalStatus") GoalStatus goalStatus,
                        Pageable pageable);

        @Query(value = """
                        SELECT
                        COUNT(CASE WHEN goal_status = 'InProgress' THEN 1 END) AS inProgress,
                        COUNT(CASE WHEN goal_status = 'completed' THEN 1 END) AS completed,
                        COUNT(CASE WHEN goal_status = 'expired' THEN 1 END) AS expired
                        FROM goals
                        WHERE user_id = :userId
                        """, nativeQuery = true)
        Map<String, Long> findGoalsCountByStatus(@Param("userId") UUID userId);

        @Query("SELECT g FROM goals g WHERE g.endDate < :today")
        List<Goal> findExpiredEndDate(LocalDateTime today);
}
