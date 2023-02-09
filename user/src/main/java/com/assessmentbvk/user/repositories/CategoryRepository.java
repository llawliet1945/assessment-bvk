package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

    Optional<Category> findByCategoryUuidAndIsdel(String categoryUuid, Integer isdel);

    List<Category> findByIsdel(Integer isdel);

}
