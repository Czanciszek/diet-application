package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.CategoryType;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.service.ProductServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2 {

    @Autowired
    ProductServiceV2 productService;

    @GetMapping
    ResponseEntity<?> getAll() {
        List<ProductType> productsList = this.productService.getAll();
        return ResponseEntity.ok(productsList);
    }

    // TODO: Consider separate document for categories
    @GetMapping(path = "/categories")
    ResponseEntity<?> getProductCategories() {
        Set<CategoryType> categoryTypes = this.productService.getProductCategories();
        return ResponseEntity.ok(categoryTypes);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody ProductType productType) {
        ProductType product = this.productService.insert(productType);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<?> update(@RequestBody ProductType productType) {
        ProductType product = this.productService.update(productType);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        this.productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
