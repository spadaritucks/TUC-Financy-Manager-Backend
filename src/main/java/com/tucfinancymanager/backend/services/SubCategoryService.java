package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.entities.SubCategory;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.CategoryRepository;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
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

    private SubCategoryResponseDTO newResponseService (SubCategory subcategory){
        return new SubCategoryResponseDTO(
                subcategory.getId(),
                subcategory.getCategory().getId(),
                subcategory.getSubcategoryName(),
                subcategory.getCreatedAt(),
                subcategory.getUpdatedAt()
        );
    }

    public PageResponseDTO<SubCategoryResponseDTO> getAllSubCategories(int page, int size) {
        Page<SubCategory> subcategories = this.subCategoryRepository.findAll(PageRequest.of(page,size));
        
        var result = subcategories.getContent().stream().map(this::newResponseService).toList();

        PageResponseDTO<SubCategoryResponseDTO> pageResponseDTO = new PageResponseDTO<>(
            subcategories.getNumber(),
            subcategories.getSize(),
            subcategories.getTotalElements(),
            subcategories.getTotalPages(),
            subcategories.isLast(),
            result
        );

        return pageResponseDTO;

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
