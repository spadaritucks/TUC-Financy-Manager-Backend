package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;
import com.tucfinancymanager.backend.entities.Transaction;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.TransactionRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        private TransactionResponseDTO newResponseService(Transaction transaction) {

                CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                                transaction.getSubCategory().getCategory().getId(),
                                transaction.getSubCategory().getCategory().getCategoryName(),
                                transaction.getSubCategory().getCategory().getCreatedAt(),
                                transaction.getSubCategory().getCategory().getUpdatedAt());

                SubCategoryResponseDTO subCategoryResponseDTO = new SubCategoryResponseDTO(
                                transaction.getSubCategory().getId(),
                                transaction.getSubCategory().getCategory().getId(),
                                transaction.getSubCategory().getSubcategoryName(),
                                transaction.getSubCategory().getCreatedAt(),
                                transaction.getSubCategory().getUpdatedAt(),
                                categoryResponseDTO);

                return new TransactionResponseDTO(
                                transaction.getId(),
                                transaction.getUser().getId(),
                                transaction.getSubCategory().getId(),
                                transaction.getTransactionType(),
                                transaction.getTransactionValue(),
                                transaction.getDescription(),
                                transaction.getTransactionDate(),
                                transaction.getRecurrent(),
                                transaction.getRecurrenceFrequency(),
                                transaction.getCreatedAt(),
                                transaction.getUpdatedAt(),
                                subCategoryResponseDTO

                );

        }

        public PageResponseDTO<TransactionResponseDTO> getCurrentMonthTransactionsByUserId(
                        UUID userId,
                        int month,
                        int year,
                        Double minValue,
                        Double maxValue,
                        String subcategory,
                        int page,
                        int size) {

                LocalDate startDate = LocalDate.of(year, month, 1);
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

                Page<Transaction> transactions = this.transactionRepository.findCurrentMonthTransactionsByUserId(
                                userId,
                                startDate,
                                endDate,
                                minValue,
                                maxValue,
                                subcategory,
                                PageRequest.of(page, size));

                var result = transactions.getContent().stream().map(this::newResponseService).toList();

                PageResponseDTO<TransactionResponseDTO> pageResponseDTO = new PageResponseDTO<>(
                                transactions.getNumber(),
                                transactions.getSize(),
                                transactions.getTotalElements(),
                                transactions.getTotalPages(),
                                transactions.isLast(),
                                result);

                return pageResponseDTO;
        }

        public Double getMonthCurrentTransactionAmountByUserId(UUID userId, int month, int year,
                        String transactionType) {
                LocalDate startDate = LocalDate.of(year, month, 1);
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                var result = this.transactionRepository.findMonthCurrentTransactionAmountByUserId(userId, startDate,
                                endDate, transactionType);
                return result;
        }

        public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
                var user = this.usersRepository.findById(transactionRequestDTO.getUserId())
                                .orElseThrow(
                                                () -> new NotFoundException("O usuario não existe"));

                var subcategory = this.subCategoryRepository.findById(transactionRequestDTO.getSubCategoryId())
                                .orElseThrow(
                                                () -> new NotFoundException("A subcategoria não existe"));

                Transaction transaction = new Transaction();

                transaction.setUser(user);
                transaction.setSubCategory(subcategory);
                transaction.setTransactionType(transactionRequestDTO.getTransactionType());
                transaction.setTransactionValue(transactionRequestDTO.getTransactionValue());
                transaction.setDescription(transactionRequestDTO.getDescription());
                transaction.setTransactionDate(transactionRequestDTO.getTransactionDate());
                transaction.setRecurrent(transactionRequestDTO.getRecurrent());
                if (transactionRequestDTO.getRecurrenceFrequency() != null)
                        transaction.setRecurrenceFrequency(transactionRequestDTO.getRecurrenceFrequency());

                transactionRepository.save(transaction);

                return newResponseService(transaction);
        }

        public TransactionResponseDTO deleteTransaction(UUID id) {
                var transaction = this.transactionRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("A Transação não existe"));

                transactionRepository.delete(transaction);

                return newResponseService(transaction);
        }
}
