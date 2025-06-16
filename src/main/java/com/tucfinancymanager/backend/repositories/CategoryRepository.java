package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryName(String categoryName);
}
