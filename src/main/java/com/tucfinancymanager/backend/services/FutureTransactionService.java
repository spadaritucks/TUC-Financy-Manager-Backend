package com.tucfinancymanager.backend.services;


import com.tucfinancymanager.backend.DTOs.futureTransactions.FutureTransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.futureTransactions.FutureTransactionResponseDTO;
import com.tucfinancymanager.backend.entities.FutureTransaction;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.FutureTransactionRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FutureTransactionService {

    @Autowired
    private FutureTransactionRepository futureTransactionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;


    private FutureTransactionResponseDTO newResponseService (FutureTransaction futureTransaction){
        return new FutureTransactionResponseDTO(
                futureTransaction.getId(),
                futureTransaction.getUser().getId(),
                futureTransaction.getSubCategory().getId(),
                futureTransaction.getTransactionType(),
                futureTransaction.getTransactionValue(),
                futureTransaction.getDescription(),
                futureTransaction.getRecurrent(),
                futureTransaction.getTransactionStatus(),
                futureTransaction.getRecurrenceFrequency(),
                futureTransaction.getCreatedAt(),
                futureTransaction.getUpdatedAt()
        );
    }

    public List<FutureTransactionResponseDTO> getAllFutureTransactions(int page, int size) {
        var futureTransactions = this.futureTransactionRepository.findAll(PageRequest.of(page, size));

        return futureTransactions.stream().map(this::newResponseService).toList();
    }

    public List<FutureTransactionResponseDTO> getFutureTransactionsByUserId (UUID userId, int page, int size) {
        var futureTransactions = this.futureTransactionRepository.findTransactionByuserId(userId, PageRequest.of(page, size));
        return futureTransactions.stream().map(this::newResponseService).toList();
    }

    public FutureTransactionResponseDTO createFutureTransaction(FutureTransactionRequestDTO futureTransactionRequestDTO) {
        var user = this.usersRepository.findById(futureTransactionRequestDTO.getUserId())
                .orElseThrow(
                        () -> new NotFoundException("O usuario não existe")
                );

        var subcategory = this.subCategoryRepository.findById(futureTransactionRequestDTO.getSubCategoryId())
                .orElseThrow(
                        () -> new NotFoundException("A subcategoria não existe")
                );

        FutureTransaction futureTransaction = new FutureTransaction();

        futureTransaction.setUser(user);
        futureTransaction.setSubCategory(subcategory);
        futureTransaction.setTransactionType(futureTransactionRequestDTO.getTransactionType());
        futureTransaction.setTransactionValue(futureTransactionRequestDTO.getTransactionValue());
        futureTransaction.setDescription(futureTransactionRequestDTO.getDescription());
        futureTransaction.setRecurrent(futureTransactionRequestDTO.getRecurrent());
        futureTransaction.setTransactionStatus(futureTransactionRequestDTO.getTransactionStatus());
        futureTransaction.setRecurrenceFrequency(futureTransactionRequestDTO.getRecurrenceFrequency());

        futureTransactionRepository.save(futureTransaction);

        return newResponseService(futureTransaction);
    }


    public FutureTransactionResponseDTO deleteFutureTransaction(UUID id) {
        var futureTransaction = this.futureTransactionRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("A Transação não existe"));

        futureTransactionRepository.delete(futureTransaction);

        return newResponseService(futureTransaction);
    }
}
