package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.repository.mongo.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<MongoCategory> getAll() {
        return this.categoryRepository.findAll();
    }
}
