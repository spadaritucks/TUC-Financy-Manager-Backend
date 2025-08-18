package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.entities.SubCategory;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.CategoryRepository;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    private SubCategoryResponseDTO newResponseService(SubCategory subcategory) {

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                subcategory.getCategory().getId(),
                subcategory.getCategory().getCategoryName(),
                subcategory.getCategory().getUser().getId(),
                subcategory.getCategory().getCreatedAt(),
                subcategory.getCategory().getUpdatedAt());

        return new SubCategoryResponseDTO(
                subcategory.getId(),
                subcategory.getCategory().getId(),
                subcategory.getSubcategoryName(),
                subcategory.getUser().getId(),
                subcategory.getCreatedAt(),
                subcategory.getUpdatedAt(),
                categoryResponseDTO
                );
    }



    public List<SubCategoryResponseDTO> getAllSubCategoriesByUserId(UUID userId) {
        List<SubCategory> subcategories = this.subCategoryRepository.findByUserId(userId);
        return subcategories.stream().map(this::newResponseService).toList();
    }

    public SubCategoryResponseDTO createSubCategory(SubCategoryRequestDTO subCategoryRequestDTO, UUID userId) {
        // Verificar se o usuário existe
        User user = this.usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        // Verificar se a subcategoria já existe para este usuário
        var subCategoryExists = this.subCategoryRepository
                .getBySubcategoryNameAndUserId(subCategoryRequestDTO.getSubcategoryName(), userId);
        if (subCategoryExists.isPresent()) {
            throw new ConflictException("A subcategoria já existe para este usuário");
        }

      
        var category = this.categoryRepository.findByIdAndUserId(
                subCategoryRequestDTO.getCategoryId(), 
                userId
        ).orElseThrow(() -> new NotFoundException("Categoria não encontrada para este usuário"));

        SubCategory subCategory = new SubCategory();
        subCategory.setCategory(category);
        subCategory.setSubcategoryName(subCategoryRequestDTO.getSubcategoryName());
        subCategory.setUser(user);

        subCategoryRepository.save(subCategory);

        return newResponseService(subCategory);
    }

    public SubCategoryResponseDTO updateUserSubCategory(UUID id, UUID userId, SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var subCategory = this.subCategoryRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new NotFoundException("A subcategoria não existe para este usuário"));

        if (subCategoryRequestUpdateDTO.getSubcategoryName() != null)
            subCategory.setSubcategoryName(subCategoryRequestUpdateDTO.getSubcategoryName());

        subCategoryRepository.save(subCategory);

        return newResponseService(subCategory);
    }

    public SubCategoryResponseDTO deleteUserSubCategory(UUID id, UUID userId) {
        var subCategory = this.subCategoryRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new NotFoundException("A subcategoria não existe para este usuário"));

        subCategoryRepository.delete(subCategory);

        return newResponseService(subCategory);
    }
}
