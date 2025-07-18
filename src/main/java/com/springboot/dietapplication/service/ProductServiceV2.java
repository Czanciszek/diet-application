package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.mongo.user.UserEntity;
import com.springboot.dietapplication.model.type.CategoryType;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.MongoProductRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceV2 {

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    DishServiceV2 dishService;

    @Autowired
    MongoProductRepository mongoProductRepository;

    public List<ProductType> getAll() {

        List<MongoProduct> mongoProductList = mongoProductRepository.findAll();

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()))
                .map(ProductType::new)
                .collect(Collectors.toList());
    }

    public List<MongoProduct> findAll(List<String> productIds) {
        return mongoProductRepository.findAllById(productIds);
    }

    public Set<CategoryType> getProductCategories() {
        List<MongoProduct> mongoProductList = mongoProductRepository.findAll();

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()))
                .map(MongoProduct::getCategory)
                .collect(Collectors.toSet());
    }

    public ProductType insert(ProductType productType) {

        // TODO: Validate all required fields
        MongoProduct mongoProduct = new MongoProduct(productType);

        UserEntity user = userDetailsService.getCurrentUser();
        mongoProduct.setUserId(user.getId());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoProduct.setCreationDate(currentDate);
        mongoProduct.setUpdateDate(currentDate);

        mongoProductRepository.save(mongoProduct);

        return new ProductType(mongoProduct);
    }

    public ProductType update(ProductType productType) {

        if (productType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id cannot be null");

        // TODO: Validate all required fields
        // TODO: Check if product with provided name doesn't override some other already existing one
        Optional<MongoProduct> mongoProduct = mongoProductRepository.findById(productType.getId());
        if (mongoProduct.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !psqlProduct.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating product");

        MongoProduct updatedProduct = new MongoProduct(productType);
        updatedProduct.setUserId(mongoProduct.get().getUserId());
        updatedProduct.setCreationDate(mongoProduct.get().getCreationDate());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        updatedProduct.setUpdateDate(currentDate);

        mongoProductRepository.save(updatedProduct);

        // Consider a separate Thread
        dishService.updateDishProducts(updatedProduct);

        return new ProductType(updatedProduct);
    }

    public void delete(String id) throws ResponseStatusException {
        Optional<MongoProduct> mongoProduct = mongoProductRepository.findById(id);
        if (mongoProduct.isEmpty()) return;

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoProduct.get().setDeletionDate(currentDate);

        mongoProductRepository.save(mongoProduct.get());
    }

}
