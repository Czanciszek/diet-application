package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.mongo.MongoDishRepository;
import com.springboot.dietapplication.repository.mongo.MongoMenuRepository;
import com.springboot.dietapplication.repository.mongo.MongoProductRepository;
import com.springboot.dietapplication.repository.mongo.MongoWeekMenuRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    MongoMenuRepository mongoMenuRepository;
    @Autowired
    MongoWeekMenuRepository mongoWeekMenuRepository;

    public List<DishType> getAll() {

        List<MongoDish> mongoProductList = mongoDishRepository.findAll();

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()))
                .map(DishType::new)
                .collect(Collectors.toList());
    }

    public List<DishUsageType> getDishUsages(String patientId) {
        List<MongoMenu> mongoMenus = mongoMenuRepository.findByPatientId(patientId);

        List<DishUsageType> dishUsageTypes = new ArrayList<>();

        mongoMenus.forEach(menu -> {

            Map<String, Integer> dishUsages = new HashMap<>();

            List<MongoWeekMenu> weekMenus = mongoWeekMenuRepository.findByMenuId(menu.getId());

            List<MongoMeal> meals = weekMenus.stream()
                    .flatMap(weekMenu -> weekMenu.getMeals().entrySet().stream()
                            .flatMap(m -> m.getValue().stream()))
                    .filter(mongoMeal -> mongoMeal.getOriginDishId() != null)
                    .collect(Collectors.toList());

            meals.forEach(meal -> {
                if (dishUsages.containsKey(meal.getOriginDishId())) {
                    int usages = dishUsages.get(meal.getOriginDishId());
                    dishUsages.put(meal.getOriginDishId(), usages + 1);
                } else {
                    dishUsages.put(meal.getOriginDishId(), 1);
                }
            });

            dishUsages.forEach((key, value) -> {
                Optional<MongoMeal> mongoMeal = meals
                        .stream()
                        .filter(meal -> meal.getOriginDishId().equals(key))
                        .findFirst();

                if (mongoMeal.isEmpty()) return;

                DishUsageType dishUsageType = new DishUsageType();
                dishUsageType.setMenuId(menu.getId());
                dishUsageType.setStartDate(menu.getStartDate());
                dishUsageType.setEndDate(menu.getEndDate());
                dishUsageType.setDishName(mongoMeal.get().getName());
                dishUsageType.setDishId(key);
                dishUsageType.setDishUsage(value);
                dishUsageTypes.add(dishUsageType);
            });
        });

        return dishUsageTypes;
    }

    public DishType insert(DishType dishType) {

        if (dishType.getFoodType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish data is not valid");

        // TODO: Validate all required fields
        // TODO: Check if product with provided name doesn't override some other already existing one

        MongoDish mongoDish = new MongoDish(dishType);

        UserEntity user = userDetailsService.getCurrentUser();
        mongoDish.setUserId(user.getId());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
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

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
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

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
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
