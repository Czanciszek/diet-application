package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.menu.DayMeal;
import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.model.menu.Menu;
import com.springboot.dietapplication.model.menu.WeekMeal;
import com.springboot.dietapplication.model.product.Product;
import com.springboot.dietapplication.model.product.ProductForDish;
import com.springboot.dietapplication.repository.mongo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/products")
public class MongoProductController {
    private final MongoProductRepository mongoProductRepository;
    private final MenuRepository menuRepository;
    private final WeekMealRepository weekMealRepository;
    private final DayMealRepository dayMealRepository;
    private final MealRepository mealRepository;

    public MongoProductController(MongoProductRepository mongoProductRepository,
                                  MenuRepository menuRepository,
                                  WeekMealRepository weekMealRepository,
                                  DayMealRepository dayMealRepository,
                                  MealRepository mealRepository) {
        this.mongoProductRepository = mongoProductRepository;
        this.menuRepository = menuRepository;
        this.weekMealRepository = weekMealRepository;
        this.dayMealRepository = dayMealRepository;
        this.mealRepository = mealRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return this.mongoProductRepository.findAll();
    }

    @GetMapping(path = "/{productId}")
    public Optional<Product> getProductById(@PathVariable("productId") String productId) {
        return this.mongoProductRepository.findById(productId);
    }

    @GetMapping(path = "/{category}/{subcategory}")
    public List<Product> getFilteredProducts(@PathVariable("category") String category,
                                             @PathVariable("subcategory") String subcategory) {

        String TAG_ANY = "*ANY*";
        List<Product> filteredProducts;
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredProducts = this.mongoProductRepository.findAll();
        } else if (category.equals(TAG_ANY)) {
            filteredProducts = this.mongoProductRepository.findBySubcategoryLike(subcategory);
        } else if (subcategory.equals(TAG_ANY)) {
            filteredProducts = this.mongoProductRepository.findByCategoryLike(category);
        } else {
            filteredProducts = this.mongoProductRepository.findByCategoryLikeAndSubcategoryLike(category, subcategory);
        }

        return filteredProducts;
    }

    @GetMapping(path = "/menu/{menuId}")
    public Map<String,Product> getMenuProducts(@PathVariable("menuId") String menuId) {
        Set<String> productIdList = new HashSet<>();
        Map<String, Product> productMap = new HashMap<>();
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

        List<Product> productList = this.mongoProductRepository.findProductsByIdIn(productIdList);
        for (Product product : productList) {
            productMap.put(product.getId(), product);
        }

        return productMap;
    }

    @GetMapping(path = "/name/{name}")
    public List<Product> getFilteredProducts(@PathVariable("name") String name) {
        return this.mongoProductRepository.findByNameLike(name);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Product> insertProduct(@RequestBody Product product) throws NoSuchFieldException {
        mongoProductRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        mongoProductRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        mongoProductRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
