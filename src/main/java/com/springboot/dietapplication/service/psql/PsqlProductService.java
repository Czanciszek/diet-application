package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.psql.PsqlCategoryRepository;
import com.springboot.dietapplication.repository.psql.PsqlFoodPropertiesRepository;
import com.springboot.dietapplication.repository.psql.PsqlProductDishRepository;
import com.springboot.dietapplication.repository.psql.PsqlProductRepository;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PsqlProductService {

    @Autowired
    PsqlProductRepository productRepository;

    @Autowired
    PsqlCategoryRepository categoryRepository;

    @Autowired
    PsqlFoodPropertiesRepository foodPropertiesRepository;

    @Autowired
    PsqlProductDishRepository productDishRepository;

    private final PsqlFoodPropertiesService foodPropertiesService;
    private final PsqlCategoryService categoryService;
    private final PsqlMenuService menuService;
    private final PsqlWeekMealService weekMealService;
    private final PsqlMealService mealService;

    public PsqlProductService(PsqlFoodPropertiesService foodPropertiesService,
                              PsqlCategoryService categoryService,
                              PsqlMenuService menuService,
                              PsqlWeekMealService weekMealService,
                              PsqlMealService mealService) {
        this.foodPropertiesService = foodPropertiesService;
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.weekMealService = weekMealService;
        this.mealService = mealService;
    }

    public List<ProductType> getAll() {
        List<PsqlProduct> products = this.productRepository.findAll();
        return convertLists(products);
    }

    public ProductType getProductById(Long productId) {
        return new ProductType();
    }

    public List<ProductType> getProductsByDishId(Long dishId) {
        List<PsqlProduct> productList = new ArrayList<>();
        List<PsqlProductDish> productDishTypeList = this.productDishRepository.findPsqlProductDishesByDishId(dishId);
        for (PsqlProductDish productDish : productDishTypeList) {
            productList.add(this.productRepository.getOne(productDish.getProductId()));
        }

        return convertLists(productList);
    }

    public List<ProductType> getFilteredProducts(String category, String subcategory) {
        String TAG_ANY = "*ANY*";
        List<ProductType> filteredMongoProducts = new ArrayList<>();
        if (category.equals(TAG_ANY) && subcategory.equals(TAG_ANY)) {
            filteredMongoProducts.addAll(getAll());
        } else if (subcategory.equals(TAG_ANY)) {
            Set<Long> categoryIds = this.categoryService.findCategoryIdsByCategoryName(category);
            List<PsqlProduct> products = this.productRepository.findPsqlProductsByCategoryIdIn(categoryIds);
            filteredMongoProducts.addAll(convertLists(products));
        } else {
            PsqlCategory psqlCategory = this.categoryService.findCategoryBySubcategoryName(subcategory);
            List<PsqlProduct> products = this.productRepository.findPsqlProductsByCategoryId(psqlCategory.getId());
            filteredMongoProducts.addAll(convertLists(products));
        }

        return filteredMongoProducts;
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

        List<PsqlProduct> psqlProductList = this.productRepository.findProductsByIdIn(productIdList);
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

    private List<ProductType> convertLists(List<PsqlProduct> products) {
        List<PsqlCategory> categories = this.categoryRepository.findAll();
        List<ProductType> productTypeList = new ArrayList<>();
        for (PsqlProduct product : products) {
            productTypeList.add(convertPsqlProductToProductType(product, categories));
        }
        return productTypeList;
    }

    private ProductType convertPsqlProductToProductType(PsqlProduct psqlProduct, List<PsqlCategory> categories) {
        ProductType productType = new ProductType(psqlProduct);

        Optional<PsqlCategory> filtered = categories.stream()
                .filter(category -> category.getId() == psqlProduct.getCategoryId())
                .findFirst();
        if (filtered.isPresent() ) {
            productType.setCategory(filtered.get().getCategory());
            productType.setSubcategory(filtered.get().getSubcategory());
        }

        FoodPropertiesType foodPropertiesType = this.foodPropertiesService.findById(psqlProduct.getFoodPropertiesId());
        productType.setFoodProperties(foodPropertiesType);

        return productType;
    }
}
