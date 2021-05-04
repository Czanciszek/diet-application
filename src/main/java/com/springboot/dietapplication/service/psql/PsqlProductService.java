package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.psql.PsqlCategoryRepository;
import com.springboot.dietapplication.repository.psql.PsqlFoodPropertiesRepository;
import com.springboot.dietapplication.repository.psql.PsqlProductRepository;
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

    private final PsqlFoodPropertiesService foodPropertiesService;
    private final PsqlCategoryService categoryService;

    PsqlProductService(PsqlFoodPropertiesService foodPropertiesService,
                       PsqlCategoryService categoryService) {
        this.foodPropertiesService = foodPropertiesService;
        this.categoryService = categoryService;
    }


    public List<ProductType> getAll() {
        List<ProductType> productTypeList = new ArrayList<>();
        List<PsqlProduct> products = this.productRepository.findAll();
        List<PsqlCategory> categories = this.categoryRepository.findAll();

        for (PsqlProduct product : products) {
            ProductType productType = new ProductType(product);

            Optional<PsqlCategory> filtered = categories.stream()
                    .filter(category -> category.getId() == product.getCategoryId())
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

    public ProductType getProductById(Long productId) {
        return new ProductType();
    }

    public List<ProductType> getFilteredProducts(String category, String subcategory) {
        return new ArrayList<>();
    }

    public Map<Long, ProductType> getMenuProducts(Long menuId) {
        return new HashMap<>();
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
}
