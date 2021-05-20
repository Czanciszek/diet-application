package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.menu.DayMeal;
import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.menu.WeekMeal;
import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.type.ProductDishType;
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
    private final MongoMenuRepository menuRepository;
    private final WeekMealRepository weekMealRepository;
    private final DayMealRepository dayMealRepository;
    private final MealRepository mealRepository;

    @Autowired
    public MongoProductService(MongoFoodPropertiesService foodPropertiesService,
                               MongoCategoryService categoryService,
                               MongoProductRepository productRepository,
                               MongoMenuRepository menuRepository,
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
        List<MongoProduct> mongoProductList = this.productRepository.findAll();
        return convertMongoProductListToProductTypes(mongoProductList);
    }

    public ProductType getProductById(String productId) {
        return new ProductType();
    }

    public List<ProductType> getFilteredProducts(String category, String subcategory) {

        String TAG_ANY = "*ANY*";
        List<ProductType> filteredMongoProducts = new ArrayList<>();
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredMongoProducts.addAll(getAll());
        } else if (subcategory.equals(TAG_ANY)) {
            Set<String> categoryIds = this.categoryService.findCategoryIdsByCategoryName(category);
            List<MongoProduct> products = this.productRepository.findMongoProductsByCategoryIdIn(categoryIds);
            filteredMongoProducts.addAll(convertMongoProductListToProductTypes(products));
        } else {
            MongoCategory mongoCategory = this.categoryService.findCategoryBySubcategoryName(subcategory);
            List<MongoProduct> products = this.productRepository.findMongoProductsByCategoryId(mongoCategory.getId());
            filteredMongoProducts.addAll(convertMongoProductListToProductTypes(products));
        }

        return filteredMongoProducts;
    }

    public Map<String, ProductType> getMenuProducts(String menuId) {
        Map<String, ProductType> productMap = new HashMap<>();

        Set<String> productIdList = new HashSet<>();
        Optional<MongoMenu> menu = this.menuRepository.findById(menuId);
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
                                    for (ProductDishType productForDish : meal.get().getProductList()) {
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
        List<ProductType> productTypeList = convertMongoProductListToProductTypes(mongoProductList);
        for (ProductType productType : productTypeList) {
            productMap.put(productType.getId(), productType);
        }

        return productMap;
    }

    public List<MongoProduct> getFilteredProducts(String name) {
        return this.productRepository.findByNameLike(name);
    }

    public ProductType insert(ProductType productType) {
        MongoProduct product = new MongoProduct(productType);

        this.foodPropertiesService.insertFoodProperties(productType.getFoodProperties());
        product.setFoodPropertiesId(productType.getFoodProperties().getId());

        MongoCategory category = this.categoryService.findCategory(productType);
        product.setCategoryId(category.getId());

        this.productRepository.save(product);
        productType.setId(product.getId());

        return productType;
    }

    public ResponseEntity<Void> delete(String id) {
        Optional<MongoProduct> product = this.productRepository.findById(id);

        this.productRepository.deleteById(id);
        product.ifPresent(mongoProduct ->
                this.foodPropertiesService.delete(mongoProduct.getFoodPropertiesId()));

        return ResponseEntity.ok().build();
    }

    private List<ProductType> convertMongoProductListToProductTypes(List<MongoProduct> products) {
        List<ProductType> productTypeList = new ArrayList<>();
        List<MongoCategory> categories = this.categoryService.getAll();

        for (MongoProduct product : products) {
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

}
