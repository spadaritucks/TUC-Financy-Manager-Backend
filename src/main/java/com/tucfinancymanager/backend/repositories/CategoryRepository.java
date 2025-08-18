package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryNameAndUserId(String categoryName, UUID userId);
    List<Category> findByUserId(UUID userId);
    Optional<Category> findByIdAndUserId(UUID id, UUID userId);
}
