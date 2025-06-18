package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
