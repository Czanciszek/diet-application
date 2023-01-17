package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.menu.PsqlAmountType;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import com.springboot.dietapplication.model.psql.product.PsqlProductsAmountTypes;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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
    AmountTypeRepository amountTypeRepository;
    @Autowired
    ProductsAmountTypesRepository productsAmountTypesRepository;

    @Autowired
    FoodPropertiesService foodPropertiesService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    JwtUserDetailsService userDetailsService;

    public List<ProductType> getAll() {

        UserEntity user = userDetailsService.getCurrentUser();
        List<PsqlProductFoodProperties> products = this.productFoodPropertiesRepository.getAllProducts(user.getId());

        return convertLists(products);
    }

    public ProductType insert(ProductType productType) {

        // TODO: Validate all required fields
        PsqlProduct product = new PsqlProduct(productType);

        // TODO: Check if product with provided name already exists

        this.foodPropertiesService.insert(productType.getFoodProperties());
        product.setFoodPropertiesId(productType.getFoodProperties().getId());

        PsqlCategory category = this.categoryService.findCategory(productType);
        product.setCategoryId(category.getId());

        UserEntity user = userDetailsService.getCurrentUser();
        product.setUserId(user.getId());

        this.productRepository.save(product);
        productType.setId(product.getId());

        storeProductsAmountTypes(productType);

        return productType;
    }

    public ProductType update(ProductType productType) {

        // TODO: Validate all required fields
        if (productType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id cannot be null");

        Optional<PsqlProduct> psqlProduct = this.productRepository.findById(productType.getId());
        if (!psqlProduct.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !psqlProduct.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating product");

        // TODO: Check if product with provided name doesn't override some other one

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

        storeProductsAmountTypes(productType);

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
        this.foodPropertiesService.delete(product.get().getFoodPropertiesId());

    }

    private List<ProductType> convertLists(List<PsqlProductFoodProperties> products) {
        List<ProductType> productTypeList = new ArrayList<>();
        for (PsqlProductFoodProperties product : products) {
            ProductType productType = new ProductType(product);

            productType.setFoodProperties(new FoodPropertiesType(product));
            productType.setAmountTypes(convertAmountTypes(product.getId()));

            productTypeList.add(productType);
        }

        return productTypeList;
    }

    private List<ProductAmountType> convertAmountTypes(long productId) {
        List<ProductAmountType> amountTypes = new ArrayList<>();

        Set<PsqlProductsAmountTypes> productsAmountTypes = productsAmountTypesRepository.findPsqlProductsAmountTypesByProductId(productId);

        productsAmountTypes.forEach( productAmountType -> {
            Optional<AmountType> optionalAmountType = AmountType.valueOf(productAmountType.getAmountTypeId());
            optionalAmountType.ifPresent(amountType -> amountTypes.add(new ProductAmountType(amountType, productAmountType.getGrams())));
        });

        return amountTypes;
    }

    private void storeProductsAmountTypes(ProductType productType) {
        this.productsAmountTypesRepository.deleteAllByProductId(productType.getId());

        if (productType.getAmountTypes() == null) return;
        Set<PsqlProductsAmountTypes> productsAmountTypes = new HashSet<>();

        for (ProductAmountType productAmountType : productType.getAmountTypes()) {

            PsqlAmountType psqlAmountType = this.amountTypeRepository.getPsqlAmountTypeByName(productAmountType.getAmountType().name());
            if (psqlAmountType == null) continue;

            PsqlProductsAmountTypes productAmountTypes = new PsqlProductsAmountTypes(productType.getId(), psqlAmountType.getId(), productAmountType.getGrams());
            productsAmountTypes.add(productAmountTypes);
        }

        this.productsAmountTypesRepository.saveAll(productsAmountTypes);
    }
}
