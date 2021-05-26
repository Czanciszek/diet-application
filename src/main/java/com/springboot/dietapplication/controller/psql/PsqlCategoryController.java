package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.service.mongo.MongoCategoryService;
import com.springboot.dietapplication.service.psql.PsqlCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/psql/categories")
public class PsqlCategoryController {

    private final PsqlCategoryService categoryService;

    public PsqlCategoryController(PsqlCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<PsqlCategory> getAll() {
        return this.categoryService.getAll();
    }

}
