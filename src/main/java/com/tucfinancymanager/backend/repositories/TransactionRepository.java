package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

        @Query(value = "SELECT * FROM transactions " +
                        "WHERE user_id = :userId " +
                        "AND transaction_date >= :startDate " +
                        "AND transaction_date <= :endDate "
                        + "ORDER BY transaction_date DESC ", nativeQuery = true)
        Page<Transaction> findCurrentMonthTransactionsByUserId(
                        @Param("userId") UUID userId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        Pageable pageable);

        @Query(value = "SELECT SUM(transaction_value) FROM transactions " +
                        "WHERE user_id = :userId " +
                        "AND transaction_date >= :startDate " +
                        "AND transaction_date <= :endDate ", nativeQuery = true)
        Double findMonthCurrentTransactionsAmountByUserId(
                        @Param("userId") UUID userId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

}
