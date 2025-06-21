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

    private SubCategoryResponseDTO newResponseService (SubCategory subcategory){
        return new SubCategoryResponseDTO(
                subcategory.getId(),
                subcategory.getCategory().getId(),
                subcategory.getSubcategoryName(),
                subcategory.getCreatedAt(),
                subcategory.getUpdatedAt()
        );
    }

    public List<SubCategoryResponseDTO> getAllSubCategories(int page, int size) {
        var subcategories = this.subCategoryRepository.findAll(PageRequest.of(page,size));
        return subcategories.stream().map(this::newResponseService).toList();

    }

    public SubCategoryResponseDTO createSubCategory(SubCategoryRequestDTO subCategoryRequestDTO) {
        var subCategoryExists = this.subCategoryRepository.getBySubcategoryName(subCategoryRequestDTO.getSubcategoryName());
        if(subCategoryExists.isPresent()){
            throw new ConflictException("A subcategoria já existe no sistema");
        }
        var category = this.categoryRepository.findById(subCategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException ("Categoria não encontrada"));

        SubCategory subCategory = new SubCategory();
        subCategory.setCategory(category);
        subCategory.setSubcategoryName(subCategoryRequestDTO.getSubcategoryName());

        subCategoryRepository.save(subCategory);

        return newResponseService(subCategory);


    }

    public SubCategoryResponseDTO updateSubCategory(UUID id, SubCategoryRequestUpdateDTO subCategoryRequestUpdateDTO) {
        var subCategory = this.subCategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("A subcategoria não existe")
        );

        if(subCategoryRequestUpdateDTO.getSubcategoryName() != null)
            subCategory.setSubcategoryName(subCategoryRequestUpdateDTO.getSubcategoryName());

        subCategoryRepository.save(subCategory);

        return newResponseService(subCategory);
    }

    public SubCategoryResponseDTO deleteSubCategory(UUID id) {
        var subCategory = this.subCategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("A subcategoria não existe")
        );

        subCategoryRepository.delete(subCategory);

        return newResponseService(subCategory);
    }
}
