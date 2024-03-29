package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Deprecated(since = "0.1.0", forRemoval = true)
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    ResponseEntity<?> getAll() {
        try {
            List<ProductType> productsList = this.productService.getAll();
            return ResponseEntity.ok(productsList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody ProductType productType) {
        try {
            ProductType product = this.productService.insert(productType);
            return ResponseEntity.ok().body(product);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(productType);
        }
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<?> update(@RequestBody ProductType productType) {
        try {
            ProductType product = this.productService.update(productType);
            return ResponseEntity.ok().body(product);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(productType);
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        try {
            this.productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}
