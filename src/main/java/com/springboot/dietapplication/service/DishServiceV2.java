package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.MongoDish;
import com.springboot.dietapplication.model.mongo.MongoDishProduct;
import com.springboot.dietapplication.model.mongo.MongoProduct;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.mongo.MongoDishRepository;
import com.springboot.dietapplication.repository.mongo.MongoProductRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishServiceV2 {

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    MongoDishRepository mongoDishRepository;
    @Autowired
    MongoProductRepository mongoProductRepository;

    public List<DishType> getAll() {

        List<MongoDish> mongoProductList = mongoDishRepository.findAll();

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()))
                .map(DishType::new)
                .collect(Collectors.toList());
    }

    public DishType insert(DishType dishType) {

        if (dishType.getFoodType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish data is not valid");

        // TODO: Validate all required fields
        // TODO: Check if product with provided name doesn't override some other already existing one

        MongoDish mongoDish = new MongoDish(dishType);

        UserEntity user = userDetailsService.getCurrentUser();
        mongoDish.setUserId(String.valueOf(user.getId()));

        DateFormat dateFormat = DateFormatter.getInstance().getIso8601Formatter();
        String currentDate = dateFormat.format(new Date());
        mongoDish.setCreationDate(currentDate);
        mongoDish.setUpdateDate(currentDate);

        // Collect original products and pass actual values
        mongoDish.setProducts(mergeDishProducts(dishType));

        mongoDishRepository.save(mongoDish);

        return new DishType(mongoDish);
    }

    public DishType update(DishType dishType) {

        // TODO: Validate all required fields
        if (dishType.getId() == null || dishType.getFoodType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish id cannot be null");

        // TODO: Check if product with provided name doesn't override some other already existing one
        Optional<MongoDish> mongoDish = mongoDishRepository.findById(dishType.getId());
        if (mongoDish.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish does not exist");

        MongoDish updatedDish = new MongoDish(dishType);

        updatedDish.setUserId(mongoDish.get().getUserId());
        updatedDish.setCreationDate(mongoDish.get().getCreationDate());

        DateFormat dateFormat = DateFormatter.getInstance().getIso8601Formatter();
        String currentDate = dateFormat.format(new Date());
        updatedDish.setUpdateDate(currentDate);

        // Collect original products and pass actual values
        updatedDish.setProducts(mergeDishProducts(dishType));

        mongoDishRepository.save(updatedDish);

        return new DishType(updatedDish);
    }

    public void delete(String id) {
        Optional<MongoDish> mongoDish = mongoDishRepository.findById(id);
        if (mongoDish.isEmpty()) return;

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");

        DateFormat dateFormat = DateFormatter.getInstance().getIso8601Formatter();
        String currentDate = dateFormat.format(new Date());
        mongoDish.get().setDeletionDate(currentDate);

        mongoDishRepository.save(mongoDish.get());
    }

    public void updateDishProducts(MongoProduct updatedProduct) {

        List<MongoDish> dishes = mongoDishRepository.findByProductsProductId(updatedProduct.getId());

        dishes.forEach(mongoDish -> mongoDish.getProducts()
                .forEach(mongoDishProduct -> {
                    if (mongoDishProduct.getProductId().equals(updatedProduct.getId())) {
                        mongoDishProduct.setName(updatedProduct.getName());
                        mongoDishProduct.setFoodProperties(updatedProduct.getFoodProperties());
                        mongoDishProduct.setAvailableAmountTypes(updatedProduct.getAmountTypes());
                    }
                })
        );

        mongoDishRepository.saveAll(dishes);
    }

    private List<MongoDishProduct> mergeDishProducts(DishType dishType) {

        List<String> productIds = dishType.getProducts()
                .stream()
                .map(ProductDishType::getProductId)
                .collect(Collectors.toList());
        List<MongoProduct> mongoProducts = getAllProductsByIdList(productIds);

        return dishType.getProducts()
                .stream()
                .map(productDishType -> {

                    Optional<MongoProduct> mongoProduct = mongoProducts
                            .stream()
                            .filter(product -> product.getId().equals(productDishType.getProductId()))
                            .findFirst();

                    return new MongoDishProduct(productDishType, mongoProduct);
                })
                .collect(Collectors.toList());
    }

    private List<MongoProduct> getAllProductsByIdList(List<String> productIds) {
        return mongoProductRepository.findAllById(productIds);
    }
}
