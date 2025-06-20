package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.category.CategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.entities.Category;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> getAllCategory(int page, int size) {
        var categories = this.categoryRepository.findAll(PageRequest.of(page, size));
        return categories.stream().map((category) -> new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        )).toList();
    }

    public CategoryResponseDTO createCategory (CategoryRequestDTO categoryRequestDTO) {
        var categoryExists = this.categoryRepository.findByCategoryName(categoryRequestDTO.getCategoryName());
        if(categoryExists.isPresent()){
            throw new ConflictException("A categoria já existe no sistema");
        }

        Category category = new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        categoryRepository.save(category);

        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );

    }

    public CategoryResponseDTO updateCategory (UUID id, CategoryRequestUpdateDTO categoryRequestUpdateDTO) {
        var category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("A categoria não existe"));
        if(categoryRequestUpdateDTO.getCategoryName() != null)
            category.setCategoryName(categoryRequestUpdateDTO.getCategoryName());
        categoryRepository.save(category);

        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );

    }

    public CategoryResponseDTO deleteCategory (UUID id) {
        var category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("A categoria não existe"));
        categoryRepository.delete(category);

        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );

    }

}
