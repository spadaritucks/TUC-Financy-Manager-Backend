package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.entities.SubCategory;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.CategoryRepository;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    private CategoryRepository categoryRepository;

    public List<SubCategoryResponseDTO> getAllSubCategories() {
        var subcategories = this.subCategoryRepository.findAll();
        return subcategories.stream().map(subcategory -> new SubCategoryResponseDTO(
                subcategory.getId(),
                subcategory.getCategory().getId(),
                subcategory.getSubCategoryName(),
                subcategory.getCreatedAt(),
                subcategory.getUpdatedAt()
        )).toList();

    }

    public SubCategoryResponseDTO createSubCategory(SubCategoryRequestDTO subCategoryRequestDTO) {
        var subCategoryExists = this.subCategoryRepository.getBySubCategoryName(subCategoryRequestDTO.getSubCategoryName());
        if(subCategoryExists.isPresent()){
            throw new ConflictException("A subcategoria já existe no sistema");
        }
        var category = this.categoryRepository.findById(subCategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException ("Categoria não encontrada"));

        SubCategory subCategory = new SubCategory();
        subCategory.setCategory(category);
        subCategory.setSubCategoryName(subCategoryRequestDTO.getSubCategoryName());

        subCategoryRepository.save(subCategory);

        return new SubCategoryResponseDTO(
                subCategory.getId(),
                subCategory.getCategory().getId(),
                subCategory.getSubCategoryName(),
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt()
        );


    }

    public SubCategoryResponseDTO updateSubCategory(UUID id, SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var subCategory = this.subCategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("A subcategoria não existe")
        );

        if(subCategoryRequestUpdateDTO.getSubCategoryName() != null)
            subCategory.setSubCategoryName(subCategoryRequestUpdateDTO.getSubCategoryName());

        subCategoryRepository.save(subCategory);

        return new SubCategoryResponseDTO(
                subCategory.getId(),
                subCategory.getCategory().getId(),
                subCategory.getSubCategoryName(),
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt()
        );
    }

    public SubCategoryResponseDTO deleteSubCategory(UUID id) {
        var subCategory = this.subCategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("A subcategoria não existe")
        );

        subCategoryRepository.delete(subCategory);

        return new SubCategoryResponseDTO(
                subCategory.getId(),
                subCategory.getCategory().getId(),
                subCategory.getSubCategoryName(),
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt()
        );
    }
}
