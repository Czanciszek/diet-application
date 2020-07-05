package com.springboot.dietapplication.Controller;

import com.springboot.dietapplication.Model.Product;
import com.springboot.dietapplication.MongoRepository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @GetMapping("/nameLike/{name}")
    public List<Product> getByNameLike(@PathVariable("name") String name) {
        return this.productRepository.findByNameLike(name);
    }

    @GetMapping("/nameRegex/{name}")
    public List<Product> getByNameRegex(@PathVariable("name") String name) {
        return this.productRepository.findByNameRegex(name);
    }
}
