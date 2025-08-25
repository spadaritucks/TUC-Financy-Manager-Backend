package com.tucfinancymanager.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tucfinancymanager.backend.DTOs.category.CategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.entities.Category;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.repositories.CategoryRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private User user;
    private CategoryRequestDTO categoryRequestDTO;
    private CategoryResponseDTO categoryResponseDTO;

    private UUID userId;
    private UUID categoryId;

    Timestamp now = Timestamp.from(Instant.now());


    @BeforeEach
    void setUp () {
        
        userId = UUID.randomUUID();
        categoryId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        category = new Category();
        category.setId(categoryId);
        category.setCategoryName("Alimentação");
        category.setUser(user);

        categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setCategoryName("Alimentação");

        categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(categoryId);
        categoryResponseDTO.setUserId(userId);
        categoryResponseDTO.setCategoryName("Alimentação");
        categoryResponseDTO.setCreated_at(now);
        categoryResponseDTO.setUpdated_at(now);

    }

    @Test
    @DisplayName("should be able return all user categories")
    void shouldBeAbleReturnAllUserCategories () {


        List<Category> categories = Arrays.asList(category); 

        when(categoryRepository.findByUserId(userId)).thenReturn(categories);

        List<CategoryResponseDTO> result = categoryService.getUserAllCategories(userId);

        CategoryResponseDTO categoryResponse= result.get(0);

        assertNotNull(result);
        assertEquals(categoryResponse.getId(), categoryId);
        assertEquals(categoryResponse.getUserId(), userId);

        verify(categoryRepository).findByUserId(userId);

   
    }
 
    @Test
    @DisplayName("should be able create new category")
    void shouldBeAbleCreateNewCategory () {

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(categoryRepository.findByCategoryNameAndUserId(categoryRequestDTO.getCategoryName(), userId)).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category savedCategory = invocation.getArgument(0);
            savedCategory.setId(categoryId);
            return savedCategory;
        });

        CategoryResponseDTO result = categoryService.createUserCategory(categoryRequestDTO, userId);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCategoryName());

        verify(categoryRepository).save(any(Category.class));


    }

}
