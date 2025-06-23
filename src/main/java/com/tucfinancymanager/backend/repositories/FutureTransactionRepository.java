package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.FutureTransaction;
import com.tucfinancymanager.backend.entities.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FutureTransactionRepository extends JpaRepository<FutureTransaction, UUID> {
    List<FutureTransaction> findTransactionByuserId(UUID userId, Pageable pageable);


}
