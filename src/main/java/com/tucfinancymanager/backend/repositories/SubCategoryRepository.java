package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
    Optional<SubCategory> getBySubcategoryNameAndUserId(String subcategoryName, UUID userId);
    List<SubCategory> findByUserId(UUID userId);
    Optional<SubCategory> findByIdAndUserId(UUID id, UUID userId);
    List<SubCategory> findByCategoryIdAndUserId(UUID categoryId, UUID userId);
}
