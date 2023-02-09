package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository <Category, Integer> {
    Optional<Category> findByCategoryUuid(String categoryUuid);

}
