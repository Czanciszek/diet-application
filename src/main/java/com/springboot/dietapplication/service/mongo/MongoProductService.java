package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.menu.DayMeal;
import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.model.menu.Menu;
import com.springboot.dietapplication.model.menu.WeekMeal;
import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.mongo.product.ProductForDish;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.mongo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MongoProductService {

    private final MongoFoodPropertiesService foodPropertiesService;
    private final MongoCategoryService categoryService;
    private final MongoProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final WeekMealRepository weekMealRepository;
    private final DayMealRepository dayMealRepository;
    private final MealRepository mealRepository;

    @Autowired
    public MongoProductService(MongoFoodPropertiesService foodPropertiesService,
                               MongoCategoryService categoryService,
                               MongoProductRepository productRepository,
                               MenuRepository menuRepository,
                               WeekMealRepository weekMealRepository,
                               DayMealRepository dayMealRepository,
                               MealRepository mealRepository) {
        this.foodPropertiesService = foodPropertiesService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.weekMealRepository = weekMealRepository;
        this.dayMealRepository = dayMealRepository;
        this.mealRepository = mealRepository;
    }

    public List<ProductType> getAll() {
        List<ProductType> productTypeList = new ArrayList<>();
        List<MongoProduct> mongoProductList = this.productRepository.findAll();
        List<MongoCategory> categories = this.categoryService.getAll();

        for (MongoProduct product : mongoProductList) {
            ProductType productType = new ProductType(product);

            Optional<MongoCategory> filtered = categories.stream()
                    .filter(category -> category.getId().equals(product.getCategoryId()))
                    .findFirst();
            if (filtered.isPresent() ) {
                productType.setCategory(filtered.get().getCategory());
                productType.setSubcategory(filtered.get().getSubcategory());
            }

            FoodPropertiesType foodPropertiesType = this.foodPropertiesService.findById(product.getFoodPropertiesId());
            productType.setFoodProperties(foodPropertiesType);

            productTypeList.add(productType);
        }

        return productTypeList;
    }

    public ProductType getProductById(String productId) {
        return new ProductType();
    }

    public List<MongoProduct> getFilteredProducts(String category, String subcategory) {

        String TAG_ANY = "*ANY*";
        List<MongoProduct> filteredMongoProducts;
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredMongoProducts = this.productRepository.findAll();
        } else if (category.equals(TAG_ANY)) {
            filteredMongoProducts = new ArrayList<>();
            //filteredProducts = this.mongoProductRepository.findBySubcategoryLike(subcategory);
        } else if (subcategory.equals(TAG_ANY)) {
            filteredMongoProducts = new ArrayList<>();
            //filteredProducts = this.mongoProductRepository.findByCategoryLike(category);
        } else {
            filteredMongoProducts = new ArrayList<>();
            //filteredProducts = this.mongoProductRepository.findByCategoryLikeAndSubcategoryLike(category, subcategory);
        }

        return filteredMongoProducts;
    }

    public Map<String, MongoProduct> getMenuProducts(String menuId) {
        Set<String> productIdList = new HashSet<>();
        Map<String, MongoProduct> productMap = new HashMap<>();
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

        List<MongoProduct> mongoProductList = this.productRepository.findProductsByIdIn(productIdList);
        for (MongoProduct mongoProduct : mongoProductList) {
            productMap.put(mongoProduct.getId(), mongoProduct);
        }

        return productMap;
    }

    public List<MongoProduct> getFilteredProducts(String name) {
        return this.productRepository.findByNameLike(name);
    }

    public ResponseEntity<ProductType> insert(ProductType productType) {
        MongoProduct product = new MongoProduct(productType);

        this.foodPropertiesService.insertFoodProperties(productType.getFoodProperties());
        product.setFoodPropertiesId(productType.getFoodProperties().getId());

        MongoCategory category = this.categoryService.findCategory(productType);
        product.setCategoryId(category.getId());

        this.productRepository.save(product);
        productType.setId(product.getId());

        return ResponseEntity.ok().body(productType);
    }

    public ResponseEntity<Void> delete(String id) {
        Optional<MongoProduct> product = this.productRepository.findById(id);

        this.productRepository.deleteById(id);
        product.ifPresent(mongoProduct ->
                this.foodPropertiesService.delete(mongoProduct.getFoodPropertiesId()));

        return ResponseEntity.ok().build();
    }

}
