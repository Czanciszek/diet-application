package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductFoodPropertiesRepository productFoodPropertiesRepository;
    @Autowired
    ProductDishRepository productDishRepository;
    @Autowired
    ProductMealRepository productMealRepository;

    @Autowired
    FoodPropertiesService foodPropertiesService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    JwtUserDetailsService userDetailsService;

    public List<ProductType> getAll() {

        UserEntity user = userDetailsService.getCurrentUser();
        List<PsqlProductFoodProperties> products = this.productFoodPropertiesRepository
                .getAllProducts()
                .stream().filter( product -> product.getUserId() == null || product.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        return convertLists(products);
    }

    public List<ProductType> getProductsByDishId(Long dishId) throws ResponseStatusException {

        List<PsqlProductFoodProperties> productList = new ArrayList<>();

        UserEntity user = userDetailsService.getCurrentUser();
        // TODO: Verify dish by user id

        List<PsqlProductDish> productDishTypeList = this.productDishRepository.findPsqlProductDishesByDishId(dishId);
        for (PsqlProductDish productDish : productDishTypeList) {
            Optional<PsqlProductFoodProperties> psqlProductFoodProperties =
                    this.productFoodPropertiesRepository.findByProductId(productDish.getProductId());
            psqlProductFoodProperties.ifPresent(productList::add);
        }

        return convertLists(productList);
    }

    public ProductType insert(ProductType productType) {

        // TODO: Validate all required fields
        PsqlProduct product = new PsqlProduct(productType);

        this.foodPropertiesService.insert(productType.getFoodProperties());
        product.setFoodPropertiesId(productType.getFoodProperties().getId());

        PsqlCategory category = this.categoryService.findCategory(productType);
        product.setCategoryId(category.getId());

        UserEntity user = userDetailsService.getCurrentUser();
        product.setUserId(user.getId());

        this.productRepository.save(product);
        productType.setId(product.getId());

        return productType;
    }

    public ProductType update(ProductType productType) {

        // TODO: Validate all required fields
        if (productType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id cannot be null");

        Optional<PsqlProduct> psqlProduct = this.productRepository.findById(productType.getId());
        if (!psqlProduct.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product does not exist");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !psqlProduct.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating product");

        PsqlProduct product = new PsqlProduct(productType);
        product.setUserId(psqlProduct.get().getUserId());

        this.foodPropertiesService.insert(productType.getFoodProperties());
        product.setFoodPropertiesId(productType.getFoodProperties().getId());

        PsqlCategory category = this.categoryService.findCategory(productType);
        product.setCategoryId(category.getId());

        if (!productType.getName().equals(psqlProduct.get().getName())) {
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

        return productType;
    }

    public void delete(Long id) throws ResponseStatusException {

        Optional<PsqlProduct> product = this.productRepository.findById(id);
        if (!product.isPresent())
            return;

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");

        this.productRepository.deleteById(id);
        product.ifPresent(psqlProduct ->
                this.foodPropertiesService.delete(psqlProduct.getFoodPropertiesId()));

    }

    private List<ProductType> convertLists(List<PsqlProductFoodProperties> products) {
        List<ProductType> productTypeList = new ArrayList<>();
        for (PsqlProductFoodProperties product : products) {
            productTypeList.add(new ProductType(product));
        }
        return productTypeList;
    }
}
