package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.service.mongo.MongoCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/categories")
public class MongoCategoryController {

    private final MongoCategoryService categoryService;

    public MongoCategoryController(MongoCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<MongoCategory> getAll() {
        return this.categoryService.getAll();
    }
}
