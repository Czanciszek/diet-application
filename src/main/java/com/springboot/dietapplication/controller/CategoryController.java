package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<PsqlCategory> getAll() {
        return this.categoryService.getAll();
    }

}
