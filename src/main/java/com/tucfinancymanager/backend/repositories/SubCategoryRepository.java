package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
    Optional<SubCategory> getBySubCategoryName(String subcategoryName);
}
