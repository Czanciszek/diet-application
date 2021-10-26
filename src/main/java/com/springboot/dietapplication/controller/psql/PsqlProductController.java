package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.service.PsqlProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/psql/products")
public class PsqlProductController {

    private final PsqlProductService productService;

    public PsqlProductController(PsqlProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductType> getAll() {
        return this.productService.getAll();
    }

    @GetMapping(path = "/{productId}")
    public ProductType getProductById(@PathVariable("productId") Long productId) {
        return this.productService.getProductById(productId);
    }

    @GetMapping(path = "/dishlist/{dishId}")
    public List<ProductType> getProductsByDishId(@PathVariable("dishId") Long dishId) {
        return this.productService.getProductsByDishId(dishId);
    }

    @GetMapping(path = "/{category}/{subcategory}")
    public List<ProductType> getFilteredProducts(@PathVariable("category") String category,
                                                  @PathVariable("subcategory") String subcategory) {
        return this.productService.getFilteredProducts(category, subcategory);
    }

    @GetMapping(path = "/menu/{menuId}")
    public Map<String, ProductType> getMenuProducts(@PathVariable("menuId") Long menuId) {
        return this.productService.getMenuProducts(menuId);
    }

    @GetMapping(path = "/name/{name}")
    public List<ProductType> getFilteredProducts(@PathVariable("name") String name) {
        return this.productService.getFilteredProducts(name);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<ProductType> insert(@RequestBody ProductType productType) throws NoSuchFieldException {
        return this.productService.insert(productType);
    }

    @PutMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<ProductType> update(@RequestBody ProductType productType) throws NoSuchFieldException {
        return this.productService.insert(productType);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        return this.productService.delete(id);
    }
}
