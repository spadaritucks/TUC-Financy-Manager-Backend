package com.tucfinancymanager.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionAmountDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionRequestDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionSubcategoryAmountDTO;
import com.tucfinancymanager.backend.DTOs.transaction.TransactionSubcategoryAmountDTO;
import com.tucfinancymanager.backend.ENUMs.TransactionRecurrenceFrequencyEnum;
import com.tucfinancymanager.backend.ENUMs.TransactionTypeEnum;
import com.tucfinancymanager.backend.entities.Category;
import com.tucfinancymanager.backend.entities.SubCategory;
import com.tucfinancymanager.backend.entities.Transaction;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.TransactionRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    // Entidades
    private User user;
    private Category category;
    private SubCategory subCategory;
    private Transaction transaction;

    // DTOs
    private TransactionRequestDTO transactionRequestDTO;
    private TransactionResponseDTO transactionResponseDTO;
    private SubCategoryResponseDTO subCategoryResponseDTO;
    private CategoryResponseDTO categoryResponseDTO;
    private PageResponseDTO<TransactionResponseDTO> pageResponseDTO;

    // IDs para testes
    private UUID userId;
    private UUID categoryId;
    private UUID subCategoryId;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        // Inicializar IDs
        userId = UUID.randomUUID();
        categoryId = UUID.randomUUID();
        subCategoryId = UUID.randomUUID();
        transactionId = UUID.randomUUID();

        Timestamp now = Timestamp.from(Instant.now());

        // Configurar User
        user = new User();
        user.setId(userId);

        // Configurar Category
        category = new Category();
        category.setId(categoryId);
        category.setCategoryName("Alimentação");
        category.setUser(user);

        // Configurar SubCategory
        subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setSubcategoryName("Restaurante");
        subCategory.setCategory(category);

        // Configurar Transaction
        transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setUser(user);
        transaction.setSubCategory(subCategory);
        transaction.setTransactionType(TransactionTypeEnum.EXPENSE);
        transaction.setTransactionValue(50.0);
        transaction.setDescription("Almoço no restaurante");
        transaction.setTransactionDate(LocalDate.now());
        transaction.setRecurrent(false);
        transaction.setRecurrenceFrequency(TransactionRecurrenceFrequencyEnum.MONTHLY);

        // Configurar CategoryResponseDTO
        categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(categoryId);
        categoryResponseDTO.setUserId(userId);
        categoryResponseDTO.setCategoryName("Alimentação");
        categoryResponseDTO.setCreated_at(now);
        categoryResponseDTO.setUpdated_at(now);

        // Configurar SubCategoryResponseDTO
        subCategoryResponseDTO = new SubCategoryResponseDTO();
        subCategoryResponseDTO.setId(subCategoryId);
        subCategoryResponseDTO.setUserId(userId);
        subCategoryResponseDTO.setCategoryId(categoryId);
        subCategoryResponseDTO.setSubcategoryName("Restaurante");
        subCategoryResponseDTO.setCreated_at(now);
        subCategoryResponseDTO.setUpdated_at(now);
        subCategoryResponseDTO.setCategory(categoryResponseDTO);

        // Configurar TransactionRequestDTO
        transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setUserId(userId);
        transactionRequestDTO.setSubCategoryId(subCategoryId);
        transactionRequestDTO.setTransactionType(TransactionTypeEnum.EXPENSE);
        transactionRequestDTO.setTransactionValue(50.0);
        transactionRequestDTO.setDescription("Almoço no restaurante");
        transactionRequestDTO.setTransactionDate(LocalDate.now());
        transactionRequestDTO.setRecurrent(false);
        transactionRequestDTO.setRecurrenceFrequency(TransactionRecurrenceFrequencyEnum.MONTHLY);

        // Configurar TransactionResponseDTO
        transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setId(transactionId);
        transactionResponseDTO.setUserId(userId);
        transactionResponseDTO.setSubCategoryId(subCategoryId);
        transactionResponseDTO.setTransactionType(TransactionTypeEnum.EXPENSE);
        transactionResponseDTO.setTransactionValue(50.0);
        transactionResponseDTO.setDescription("Almoço no restaurante");
        transactionResponseDTO.setTransactionDate(LocalDate.now());
        transactionResponseDTO.setRecurrent(false);
        transactionResponseDTO.setRecurrenceFrequency(TransactionRecurrenceFrequencyEnum.MONTHLY);
        transactionResponseDTO.setCreated_at(now);
        transactionResponseDTO.setUpdated_at(now);
        transactionResponseDTO.setSubcategory(subCategoryResponseDTO);

    }

    @Test
    @DisplayName("should be able return current month transactions by user id")
    void shouldBeAbleReturnCurrentMonthTransactionsByUserId() {

        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Transaction> transactions = Arrays.asList(transaction);
        Page<Transaction> transactionPage = new PageImpl<>(transactions);
        when(transactionRepository.findCurrentMonthTransactionsByUserId(userId, startDate, endDate,
                10.0, 60.0, "Almoço no restaurante",
                PageRequest.of(0, 10))).thenReturn(transactionPage);

        PageResponseDTO<TransactionResponseDTO> result = transactionService.getCurrentMonthTransactionsByUserId(userId,
                8, 2025,
                10.0, 60.0, "Almoço no restaurante", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(1, result.getContent().size());

        TransactionResponseDTO transactionResponse = result.getContent().get(0);
        assertEquals(transactionId, transactionResponse.getId());
        assertEquals(userId, transactionResponse.getUserId());
        assertEquals(50.0, transactionResponse.getTransactionValue());
        assertEquals(TransactionTypeEnum.EXPENSE, transactionResponse.getTransactionType());

        verify(transactionRepository).findCurrentMonthTransactionsByUserId(
                userId, startDate, endDate, 10.0, 60.0, "Almoço no restaurante", PageRequest.of(0, 10));

    }

    @Test
    @DisplayName("should be able return expense, income and total amount beetwen start and endDate")
    void shouldBeAbleReturnExpenseIncomeAndTotalAmountBeetweenStartAndEndDate() {

        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        Map<String, BigDecimal> transactionAmountMap = Map.of(
                "income", new BigDecimal("1500.00"),
                "expense", new BigDecimal("800.00"),
                "total", new BigDecimal("700.00"));

        when(transactionRepository.findMonthCurrentTransactionAmountByUserId(userId, startDate, endDate))
                .thenReturn(transactionAmountMap);

        TransactionAmountDTO result = transactionService.getMonthCurrentTransactionAmountByUserId(userId, 8, 2025);

        assertNotNull(result);
        assertEquals(new BigDecimal("1500.00"), result.getIncome());
        assertEquals(new BigDecimal("800.00"), result.getExpense());
        assertEquals(new BigDecimal("700.00"), result.getTotal());

        verify(transactionRepository).findMonthCurrentTransactionAmountByUserId(userId, startDate, endDate);
    }

    @Test
    @DisplayName("should be able return expense and income amount grouped per subcategories")
    void shouldBeAbleReturnExpenseAndIncomeAmountGroupedPerSubcategories() {

        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // Criar os Object[] que simulam o retorno do repository
        Object[] row1 = new Object[]{"Restaurante", "EXPENSE", new BigDecimal("150.50")};
        Object[] row2 = new Object[]{"Salário", "INCOME", new BigDecimal("3000.00")};
        Object[] row3 = new Object[]{"Supermercado", "EXPENSE", new BigDecimal("200.75")};
        
        List<Object[]> mockResult = Arrays.asList(row1, row2, row3);

        when(transactionRepository.findAmountCurrentTransactionsBySubCategory(userId, startDate, endDate))
                .thenReturn(mockResult);

        List<TransactionSubcategoryAmountDTO> result = transactionService
                .getAmountCurrentTransactionsBySubCategory(userId, 8, 2025);

        assertNotNull(result);
        assertEquals(3, result.size());

        // Verificar primeiro item
        TransactionSubcategoryAmountDTO firstItem = result.get(0);
        assertEquals("Restaurante", firstItem.getSubcategoryName());
        assertEquals("EXPENSE", firstItem.getTransactionType());
        assertEquals(new BigDecimal("150.50"), firstItem.getAmount());

        // Verificar segundo item
        TransactionSubcategoryAmountDTO secondItem = result.get(1);
        assertEquals("Salário", secondItem.getSubcategoryName());
        assertEquals("INCOME", secondItem.getTransactionType());
        assertEquals(new BigDecimal("3000.00"), secondItem.getAmount());

        // Verificar terceiro item
        TransactionSubcategoryAmountDTO thirdItem = result.get(2);
        assertEquals("Supermercado", thirdItem.getSubcategoryName());
        assertEquals("EXPENSE", thirdItem.getTransactionType());
        assertEquals(new BigDecimal("200.75"), thirdItem.getAmount());

        verify(transactionRepository).findAmountCurrentTransactionsBySubCategory(userId, startDate, endDate);
    }

    @Test
    @DisplayName("Should be able create new transaction")
    void shouldBeAbleCreateNewTransaction() {

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(subCategoryRepository.findByIdAndUserId(subCategoryId, userId)).thenReturn(Optional.of(subCategory));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setId(transactionId); // Simula o comportamento do JPA que seta o ID
            return savedTransaction;
        });

        TransactionResponseDTO result = transactionService.createTransaction(transactionRequestDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getUserId());
        assertNotNull(result.getSubCategoryId());
        assertNotNull(result.getTransactionType());
        assertNotNull(result.getTransactionValue());

        verify(transactionRepository).save(any(Transaction.class));

    }



}
