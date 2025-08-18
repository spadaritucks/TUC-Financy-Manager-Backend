package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionAmountDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionSubcategoryAmountDTO;
import com.tucfinancymanager.backend.entities.Transaction;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.TransactionRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
                                transaction.getUser().getId(),
                                transaction.getSubCategory().getCategory().getCreatedAt(),
                                transaction.getSubCategory().getCategory().getUpdatedAt());

                SubCategoryResponseDTO subCategoryResponseDTO = new SubCategoryResponseDTO(
                                transaction.getSubCategory().getId(),
                                transaction.getSubCategory().getCategory().getId(),
                                transaction.getSubCategory().getSubcategoryName(),
                                transaction.getUser().getId(),
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

        public TransactionAmountDTO getMonthCurrentTransactionAmountByUserId(UUID userId, int month, int year) {
                LocalDate startDate = LocalDate.of(year, month, 1);
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                Map<String, BigDecimal> result = this.transactionRepository
                                .findMonthCurrentTransactionAmountByUserId(userId, startDate, endDate);

                TransactionAmountDTO transactionAmountDTO = new TransactionAmountDTO();
                transactionAmountDTO.setIncome(result.get("income"));
                transactionAmountDTO.setExpense(result.get("expense"));
                transactionAmountDTO.setTotal(result.get("total"));

                return transactionAmountDTO;
        }

        public List<TransactionSubcategoryAmountDTO> getAmountCurrentTransactionsBySubCategory(UUID userId, int month, int year) {

                LocalDate startDate = LocalDate.of(year, month, 1);
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

                List<Object[]> result = this.transactionRepository.findAmountCurrentTransactionsBySubCategory(userId, startDate, endDate);
    
                return result.stream()
                        .map(row -> new TransactionSubcategoryAmountDTO(
                                (String) row[0],   
                                (String) row[1],  
                                (BigDecimal) row[2]    
                        ))
                        .toList();
        }

        public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
                var user = this.usersRepository.findById(transactionRequestDTO.getUserId())
                                .orElseThrow(
                                                () -> new NotFoundException("O usuario não existe"));

                var subcategory = this.subCategoryRepository.findByIdAndUserId(
                        transactionRequestDTO.getSubCategoryId(), 
                        transactionRequestDTO.getUserId())
                                .orElseThrow(
                                                () -> new NotFoundException("A subcategoria não existe para este usuário"));

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

        public TransactionResponseDTO deleteTransaction(UUID id, UUID userId) {
                var transaction = this.transactionRepository.findByIdAndUserId(id, userId)
                                .orElseThrow(() -> new NotFoundException("A Transação não existe para este usuário"));

                transactionRepository.delete(transaction);

                return newResponseService(transaction);
        }
}
