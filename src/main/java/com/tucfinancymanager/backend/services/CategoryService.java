package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.category.CategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.entities.Category;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.CategoryRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    private CategoryResponseDTO newResponseService (Category category){
        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getUser().getId(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public List<CategoryResponseDTO> getAllCategoryByUserId(UUID userId, int page, int size) {
        var categories = this.categoryRepository.findByUserId(userId, PageRequest.of(page, size));
        return categories.stream().map(this::newResponseService).toList();
    }

    public List<CategoryResponseDTO> getAllCategoryByUserId(UUID userId) {
        var categories = this.categoryRepository.findByUserId(userId);
        return categories.stream().map(this::newResponseService).toList();
    }

    public CategoryResponseDTO createCategory (CategoryRequestDTO categoryRequestDTO) {
        // Verificar se o usuário existe
        User user = this.usersRepository.findById(categoryRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        // Verificar se a categoria já existe para este usuário
        var categoryExists = this.categoryRepository.findByCategoryNameAndUserId(
                categoryRequestDTO.getCategoryName(), 
                categoryRequestDTO.getUserId()
        );
        if(categoryExists.isPresent()){
            throw new ConflictException("A categoria já existe para este usuário");
        }

        Category category = new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        category.setUser(user);
        categoryRepository.save(category);

        return newResponseService(category);
    }

    public CategoryResponseDTO updateCategory (UUID id, UUID userId, CategoryRequestUpdateDTO categoryRequestUpdateDTO) {
        var category = this.categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("A categoria não existe para este usuário"));
        
        if(categoryRequestUpdateDTO.getCategoryName() != null)
            category.setCategoryName(categoryRequestUpdateDTO.getCategoryName());
        
        categoryRepository.save(category);

        return newResponseService(category);
    }

    public CategoryResponseDTO deleteCategory (UUID id, UUID userId) {
        var category = this.categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("A categoria não existe para este usuário"));
        categoryRepository.delete(category);

        return newResponseService(category);
    }
}
