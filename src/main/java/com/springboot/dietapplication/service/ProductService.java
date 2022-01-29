package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired ProductRepository productRepository;
    @Autowired ProductFoodPropertiesRepository productFoodPropertiesRepository;
    @Autowired ProductDishRepository productDishRepository;
    @Autowired ProductMealRepository productMealRepository;

    @Autowired FoodPropertiesService foodPropertiesService;
    @Autowired CategoryService categoryService;
    @Autowired MenuService menuService;
    @Autowired WeekMealService weekMealService;
    @Autowired MealService mealService;

    public List<ProductType> getAll() {
        List<PsqlProductFoodProperties> products = this.productFoodPropertiesRepository.getAllProducts();
        return convertLists(products);
    }

    public ProductType getProductById(Long productId) {
        return new ProductType();
    }

    public List<ProductType> getProductsByDishId(Long dishId) {
        List<PsqlProductFoodProperties> productList = new ArrayList<>();
        List<PsqlProductDish> productDishTypeList = this.productDishRepository.findPsqlProductDishesByDishId(dishId);
        for (PsqlProductDish productDish : productDishTypeList) {
            Optional<PsqlProductFoodProperties> psqlProductFoodProperties =
                    this.productFoodPropertiesRepository.findByProductId(productDish.getProductId());
            psqlProductFoodProperties.ifPresent(productList::add);
        }

        return convertLists(productList);
    }

    public List<ProductType> getFilteredProducts(String category, String subcategory) {
        String TAG_ANY = "*ANY*";
        List<ProductType> filteredPsqlProducts = new ArrayList<>();
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredPsqlProducts.addAll(getAll());
        } else if (subcategory.equals(TAG_ANY)) {
            Set<Long> categoryIds = this.categoryService.findCategoryIdsByCategoryName(category);
            List<PsqlProductFoodProperties> products = this.productFoodPropertiesRepository.findPsqlProductsByCategoryIdIn(categoryIds);
            filteredPsqlProducts.addAll(convertLists(products));
        } else {
            PsqlCategory psqlCategory = this.categoryService.findCategoryBySubcategoryName(subcategory);
            List<PsqlProductFoodProperties> products = this.productFoodPropertiesRepository.findPsqlProductsByCategoryId(psqlCategory.getId());
            filteredPsqlProducts.addAll(convertLists(products));
        }

        return filteredPsqlProducts;
    }

    public Map<String, ProductType> getMenuProducts(Long menuId) {
        Map<String, ProductType> productMap = new HashMap<>();
        Set<Long> productIdList = new HashSet<>();

        MenuType menu = this.menuService.getMenuById(menuId);
        for (String weekMealId : menu.getWeekMealList()) {
            WeekMealType weekMealType = this.weekMealService.getWeekMealById(Long.parseLong(weekMealId));
            List<MealType> mealTypeList = this.mealService.getMealsByDayMealList(weekMealType.getDayMealList());
            for (MealType mealType : mealTypeList) {
                for (ProductDishType productDishType : mealType.getProductList()) {
                    String productId = productDishType.getProductId();
                    productIdList.add(Long.parseLong(productId));
                }

            }
        }

        List<PsqlProductFoodProperties> psqlProductList = this.productFoodPropertiesRepository.findProductsByIdIn(productIdList);
        List<ProductType> productTypeList = convertLists(psqlProductList);
        for (ProductType productType : productTypeList) {
            productMap.put(productType.getId(), productType);
        }

        return productMap;
    }

    public List<ProductType> getFilteredProducts(String name) {
        return new ArrayList<>();
    }

    public ResponseEntity<ProductType> insert(ProductType productType) throws NoSuchFieldException {
        PsqlProduct product = new PsqlProduct(productType);

        this.foodPropertiesService.insert(productType.getFoodProperties());
        long foodPropertiesId = Long.parseLong(productType.getFoodProperties().getId());
        product.setFoodPropertiesId(foodPropertiesId);

        PsqlCategory category = this.categoryService.findCategory(productType);
        product.setCategoryId(category.getId());

        if (product.getId() > 0) {
            List<PsqlProductDish> productDishList = this.productDishRepository.findPsqlProductDishesByProductId(product.getId());
            if (productDishList.size() > 0) {
                productDishList.forEach( productDish -> productDish.setProductName(product.getName()));
                this.productDishRepository.saveAll(productDishList);
            }

            List<PsqlProductMeal> productMealList = this.productMealRepository.findPsqlProductDishesByProductId(product.getId());
            if (productMealList.size() > 0) {
                productMealList.forEach( productMeal -> productMeal.setProductName(product.getName()));
                this.productMealRepository.saveAll(productMealList);
            }
        }

        this.productRepository.save(product);
        productType.setId(String.valueOf(product.getId()));

        return ResponseEntity.ok().body(productType);
    }

    public ResponseEntity<Void> delete(Long id) {
        Optional<PsqlProduct> product = this.productRepository.findById(id);

        this.productRepository.deleteById(id);
        product.ifPresent(psqlProduct ->
                this.foodPropertiesService.delete(psqlProduct.getFoodPropertiesId()));

        return ResponseEntity.ok().build();
    }

    private List<ProductType> convertLists(List<PsqlProductFoodProperties> products) {
        List<ProductType> productTypeList = new ArrayList<>();
        for (PsqlProductFoodProperties product : products) {
            productTypeList.add(new ProductType(product));
        }
        return productTypeList;
    }
}
