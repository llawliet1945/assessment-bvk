package com.assessmentbvk.user.controllers;

import com.assessmentbvk.user.dto.RequestCategory;
import com.assessmentbvk.user.services.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@ControllerAdvice
@RequestMapping(value = "/category", produces= MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/detail/{categoryUuid}")
    public ResponseEntity<String> detailCategory(@PathVariable String categoryUuid) throws JsonProcessingException {
        return categoryService.detailCategory(categoryUuid);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<String> listCategory() throws JsonProcessingException {
        return categoryService.listCategory();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addCategory(@RequestHeader(name = "userId") String userId, @RequestBody RequestCategory request) throws JsonProcessingException {
        return categoryService.addCategory(Integer.parseInt(userId), request);
    }

    @PutMapping(value = "/update/{categoryUuid}")
    public ResponseEntity<String> updateCategory(@RequestHeader(name = "userId") String userId, @PathVariable String categoryUuid, @RequestBody RequestCategory request) throws JsonProcessingException {
        return categoryService.updateCategory(Integer.parseInt(userId), categoryUuid, request);
    }

    @DeleteMapping(value = "/delete/{categoryUuid}")
    public ResponseEntity<String> deleteCategory(@PathVariable String categoryUuid) throws JsonProcessingException {
        return categoryService.deleteCategory(categoryUuid);
    }

}
