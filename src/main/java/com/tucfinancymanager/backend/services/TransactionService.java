package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.transaction.TransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionStatusDTO;
import com.tucfinancymanager.backend.entities.Transaction;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.TransactionRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;


    private TransactionResponseDTO newResponseService (Transaction transaction){
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getSubCategory().getId(),
                transaction.getTransactionType(),
                transaction.getTransactionValue(),
                transaction.getDescription(),
                transaction.getTransactionStatus(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

    public List<TransactionResponseDTO> getAllTransactions(int page, int size) {
        var transactions = this.transactionRepository.findAll(PageRequest.of(page, size));

        return transactions.stream().map(this::newResponseService).toList();
    }

    public List<TransactionResponseDTO> getTransactionsByUserId (UUID userId, int page, int size) {
        var transactions = this.transactionRepository.findTransactionByuserId(userId, PageRequest.of(page, size));
        return transactions.stream().map(this::newResponseService).toList();
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        var user = this.usersRepository.findById(transactionRequestDTO.getUserId())
                .orElseThrow(
                        () -> new NotFoundException("O usuario não existe")
                );

        var subcategory = this.subCategoryRepository.findById(transactionRequestDTO.getSubCategoryId())
                .orElseThrow(
                        () -> new NotFoundException("A subcategoria não existe")
                );

        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setSubCategory(subcategory);
        transaction.setTransactionType(transactionRequestDTO.getTransactionType());
        transaction.setTransactionValue(transactionRequestDTO.getTransactionValue());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setTransactionStatus(transactionRequestDTO.getTransactionStatus());

        transactionRepository.save(transaction);

        return newResponseService(transaction);
    }

    public TransactionResponseDTO updateTransactionStatus (UUID id, TransactionStatusDTO transactionStatusDTO){
        var transaction = this.transactionRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("A Transação não existe"));

        transaction.setTransactionStatus(transactionStatusDTO.getTransactionStatus());
        transactionRepository.save(transaction);

        return newResponseService(transaction);
    }

    public TransactionResponseDTO deleteTransaction(UUID id) {
        var transaction = this.transactionRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("A Transação não existe"));

        transactionRepository.delete(transaction);

        return newResponseService(transaction);
    }
}
