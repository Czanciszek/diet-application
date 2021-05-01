package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.psql.product.Product;
import com.springboot.dietapplication.repository.psql.PSQLProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2/products")
public class PSQLProductController {

    @Autowired
    PSQLProductRepository psqlProductRepository;

    @GetMapping
    public List<Product> getAll() {
        List<Product> productList =  this.psqlProductRepository.findAllProducts();
        return productList;
    }

    @GetMapping(path = "/{productId}")
    public Optional<Product> getProductById(@PathVariable("productId") Long productId) {
        return this.psqlProductRepository.findById(productId);
    }

    @GetMapping(path = "/{category}/{subcategory}")
    public List<Product> getFilteredProducts(@PathVariable("category") String category,
                                             @PathVariable("subcategory") String subcategory) {

        String TAG_ANY = "*ANY*";
        List<Product> filteredProducts;
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredProducts = this.psqlProductRepository.findAll();
        } else if (category.equals(TAG_ANY)) {
            //filteredProducts = this.psqlProductRepository.findBySubcategoryNameLike(subcategory);
            filteredProducts = new ArrayList<>();
        } else if (subcategory.equals(TAG_ANY)) {
            //filteredProducts = this.psqlProductRepository.findByCategoryNameLike(category);
            filteredProducts = new ArrayList<>();
        } else {
            //filteredProducts = this.psqlProductRepository.findByCategoryNameLikeAndSubcategoryNameLike(category, subcategory);
            filteredProducts = new ArrayList<>();
        }

        return filteredProducts;
    }

    @GetMapping(path = "/menu/{menuId}")
    public Map<Long,Product> getMenuProducts(@PathVariable("menuId") String menuId) {
        Set<Long> productIdList = new HashSet<>();
        Map<Long, Product> productMap = new HashMap<>();
        /*
        Optional<Menu> menu = this.menuRepository.findById(menuId);
        if (menu.isPresent()) {
            for (String weekMealId : menu.get().getWeekMealList()) {
                Optional<WeekMeal> weekMeal = this.weekMealRepository.findById(weekMealId);
                if (weekMeal.isPresent()) {
                    for (String dayMealId : weekMeal.get().getDayMealList()) {
                        Optional<DayMeal> dayMeal = this.dayMealRepository.findById(dayMealId);
                        if (dayMeal.isPresent() && dayMeal.get().getMealList() != null) {
                            for (String mealId : dayMeal.get().getMealList()) {
                                Optional<Meal> meal = this.mealRepository.findById(mealId);
                                if (meal.isPresent()) {
                                    for (ProductForDish productForDish : meal.get().getProductList()) {
                                        productIdList.add(productForDish.getProductId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        */

        List<Product> productList = this.psqlProductRepository.findProductsByIdIn(productIdList);
        for (Product product : productList) {
            productMap.put(product.getId(), product);
        }

        return productMap;
    }

    @GetMapping(path = "/name/{name}")
    public List<Product> getFilteredProducts(@PathVariable("name") String name) {
        //return this.psqlProductRepository.findByNameLike(name);
        return new ArrayList<>();
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Product> insertProduct(@RequestBody Product product) throws NoSuchFieldException {
        psqlProductRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        psqlProductRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        psqlProductRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
