package com.assessmentbvk.user.services;

import com.assessmentbvk.user.dto.RequestCategory;
import com.assessmentbvk.user.dto.ResponseDetailCategory;
import com.assessmentbvk.user.models.Category;
import com.assessmentbvk.user.repositories.CategoryRepository;
import com.assessmentbvk.user.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> listCategory() throws JsonProcessingException {
        List<Category> categories = categoryRepository.findByIsdel(0);
        if (categories.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        List<ResponseDetailCategory> response = categories.stream().map(x -> modelMapper.map(x, ResponseDetailCategory.class)).collect(Collectors.toList());
        return GenerateResponse.success("Get data success", response);
    }

    public ResponseEntity<String> detailCategory(String categoryUuid) throws JsonProcessingException {
        Optional<Category> category = categoryRepository.findByCategoryUuidAndIsdel(categoryUuid, 0);
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        ResponseDetailCategory response = modelMapper.map(category.get(), ResponseDetailCategory.class);
        return GenerateResponse.success("Get data success", response);
    }

    public ResponseEntity<String> addCategory(Integer userId, RequestCategory request) throws JsonProcessingException {
        Category category = modelMapper.map(request, Category.class);
        category.setCategoryUuid(UUID.randomUUID().toString());
        category.setIsdel(0);
        category.setCreatedBy(userId);
        category.setCreatedDate(new Date());
        categoryRepository.save(category);
        return GenerateResponse.success("Add new category success", null);
    }

    public ResponseEntity<String> updateCategory(Integer userId, String categoryUuid, RequestCategory request) throws JsonProcessingException {
        Optional<Category> category = categoryRepository.findByCategoryUuidAndIsdel(categoryUuid, 0);
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        category.get().setCategoryName(request.getCategoryName());
        category.get().setUpdatedBy(userId);
        category.get().setUpdatedDate(new Date());
        categoryRepository.save(category.get());
        return GenerateResponse.success("Update category success", null);
    }

    public ResponseEntity<String> deleteCategory(String categoryUuid) throws JsonProcessingException {
        Optional<Category> category = categoryRepository.findByCategoryUuidAndIsdel(categoryUuid, 0);
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        category.get().setIsdel(1);
        categoryRepository.save(category.get());
        return GenerateResponse.success("Delete category success", null);
    }

}
