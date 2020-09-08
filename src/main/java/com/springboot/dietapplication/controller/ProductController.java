package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.product.Product;
import com.springboot.dietapplication.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @GetMapping(path = "/{category}/{subcategory}")
    public List<Product> getFilteredProducts(@PathVariable("category") String category,
                                             @PathVariable("subcategory") String subcategory) {

        String TAG_ANY = "*ANY*";
        List<Product> filteredProducts;
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredProducts = this.productRepository.findAll();
        } else if (category.equals(TAG_ANY)) {
            filteredProducts = this.productRepository.findBySubcategoryLike(subcategory);
        } else if (subcategory.equals(TAG_ANY)) {
            filteredProducts = this.productRepository.findByCategoryLike(category);
        } else {
            filteredProducts = this.productRepository.findByCategoryLikeAndSubcategoryLike(category, subcategory);
        }

        return filteredProducts;
    }

    @GetMapping(path = "/name/{name}")
    public List<Product> getFilteredProducts(@PathVariable("name") String name) {
        return this.productRepository.findByNameLike(name);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Product> insertProduct(@RequestBody Product product) throws NoSuchFieldException {
        productRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
