package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.DTOs.transaction.TransactionAmountDTO;
import com.tucfinancymanager.backend.entities.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
                SELECT t
                FROM transactions t
                JOIN FETCH t.subCategory s
                JOIN FETCH s.category c
                WHERE t.user.id = :userId
                  AND t.transactionDate >= :startDate
                  AND t.transactionDate <= :endDate
                  AND (:minValue IS NULL OR t.transactionValue >= :minValue)
                  AND (:maxValue IS NULL OR t.transactionValue <= :maxValue)
                  AND (:subcategory IS NULL OR s.subcategoryName LIKE :subcategory)
                ORDER BY t.transactionDate DESC
            """)
    Page<Transaction> findCurrentMonthTransactionsByUserId(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("minValue") Double minValue,
            @Param("maxValue") Double maxValue,
            @Param("subcategory") String subcategory,
            Pageable pageable);

    @Query(value = """
            SELECT
                SUM(CASE WHEN transaction_type = 'INCOME' THEN transaction_value ELSE 0 END) AS income,
                SUM(CASE WHEN transaction_type = 'EXPENSE' THEN transaction_value ELSE 0 END) AS expense,
                SUM(CASE WHEN transaction_type = 'INCOME' THEN transaction_value ELSE 0 END) -
                SUM(CASE WHEN transaction_type = 'EXPENSE' THEN transaction_value ELSE 0 END) AS total
            FROM
                transactions
            WHERE
                user_id = :userId
                AND transaction_date >= :startDate
                AND transaction_date <= :endDate
                                        """, nativeQuery = true)
    Map<String, BigDecimal> findMonthCurrentTransactionAmountByUserId(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
