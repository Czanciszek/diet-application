package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/psql/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<PsqlCategory> getAll() {
        return this.categoryService.getAll();
    }

}
