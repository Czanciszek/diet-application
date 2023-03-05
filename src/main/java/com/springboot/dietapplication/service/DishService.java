package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.dish.PsqlDishUsage;
import com.springboot.dietapplication.model.psql.menu.PsqlAmountType;
import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import com.springboot.dietapplication.model.psql.product.PsqlProductsAmountTypes;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;
    @Autowired
    ProductDishRepository productDishRepository;
    @Autowired
    FoodTypeRepository foodTypeRepository;
    @Autowired
    AmountTypeRepository amountTypeRepository;
    @Autowired
    ProductsAmountTypesRepository productsAmountTypesRepository;
    @Autowired
    DishUsageRepository dishUsageRepository;

    @Autowired
    JwtUserDetailsService userDetailsService;

    public List<DishType> getAll() {

        List<PsqlDish> dishes = this.dishRepository.findAll();
//        UserEntity user = userDetailsService.getCurrentUser();
//        List<PsqlDish> dishes = this.dishRepository.findAllWhereIsSystemOrByUserId(user.getId());

        return convertLists(dishes);
    }

    public List<DishUsageType> getDishUsages(Long patientId) {

        List<PsqlDishUsage> psqlDishUsages = this.dishUsageRepository.findPsqlDishUsageByPatientId(patientId);
        return psqlDishUsages.stream().map(DishUsageType::new).collect(Collectors.toList());
    }

    public DishType insert(DishType dishType) {

        // TODO: Validate all required fields
        if (dishType.getFoodType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish data is not valid");

        // TODO: Check if dish with provided name already exists

        PsqlDish psqlDish = new PsqlDish(dishType);

        UserEntity user = userDetailsService.getCurrentUser();
        psqlDish.setUserId(user.getId());

        this.dishRepository.save(psqlDish);

        for (ProductDishType productDish : dishType.getProducts()) {
            PsqlProductDish psqlProductDish = new PsqlProductDish(productDish);
            psqlProductDish.setDishId(psqlDish.getId());

            if (productDish.getAmountType() != null) {
                PsqlAmountType amountType = this.amountTypeRepository.getPsqlAmountTypeByName(productDish.getAmountType().toString());
                psqlProductDish.setAmountTypeId(amountType.getId());
            }

            this.productDishRepository.save(psqlProductDish);
        }

        PsqlFoodType foodType = this.foodTypeRepository.getPsqlFoodTypeByName(dishType.getFoodType().toString());
        psqlDish.setFoodTypeId(foodType.getId());

        this.dishRepository.save(psqlDish);
        dishType.setId(psqlDish.getId());

        return dishType;
    }

    public DishType update(DishType dishType) {

        // TODO: Validate all required fields
        if (dishType.getId() == null || dishType.getFoodType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish id cannot be null");

        Optional<PsqlDish> psqlDish = this.dishRepository.findById(dishType.getId());
        if (psqlDish.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dish does not exist");

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !psqlDish.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating dish");

        // TODO: Check if dish with provided name already exists

        PsqlDish dish = new PsqlDish(dishType);
        dish.setUserId(psqlDish.get().getUserId());

        this.dishRepository.save(dish);

        this.productDishRepository.deletePsqlProductDishesByDishId(dish.getId());

        for (ProductDishType productDish : dishType.getProducts()) {
            PsqlProductDish psqlProductDish = new PsqlProductDish(productDish);
            psqlProductDish.setDishId(dish.getId());

            if (productDish.getAmountType() != null) {
                PsqlAmountType amountType = this.amountTypeRepository.getPsqlAmountTypeByName(productDish.getAmountType().toString());
                psqlProductDish.setAmountTypeId(amountType.getId());
            }

            this.productDishRepository.save(psqlProductDish);
        }

        PsqlFoodType foodType = this.foodTypeRepository.getPsqlFoodTypeByName(dishType.getFoodType().toString());
        dish.setFoodTypeId(foodType.getId());

        this.dishRepository.save(dish);

        return dishType;
    }

    public void delete(Long id) {
        Optional<PsqlDish> dish = this.dishRepository.findById(id);
        if (dish.isEmpty())
            return;

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !dish.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting dish attempt");

        List<PsqlProductDish> productDishList =
                this.productDishRepository.findPsqlProductDishesByDishId(id);
        this.productDishRepository.deleteAll(productDishList);

        this.dishRepository.deleteById(id);
    }

    private List<DishType> convertLists(List<PsqlDish> psqlDayMeals) {
        return psqlDayMeals.stream().map(this::convertPsqlDishToDishType).collect(Collectors.toList());
    }

    private DishType convertPsqlDishToDishType(PsqlDish psqlDish) {
        DishType dishType = new DishType(psqlDish);

        List<ProductDishType> productList = new ArrayList<>();
        List<PsqlProductDish> productDishList =
                this.productDishRepository.findPsqlProductDishesByDishId(psqlDish.getId());
        for (PsqlProductDish productDish : productDishList) {
            ProductDishType productDishType = new ProductDishType(productDish);

            if (productDish.getAmountTypeId() != null) {
                Optional<PsqlAmountType> amountTypeOptional = this.amountTypeRepository.findById(productDish.getAmountTypeId());
                if (amountTypeOptional.isPresent()) {
                    AmountType amountType = AmountType.valueOf(amountTypeOptional.get().getName());
                    productDishType.setAmountType(amountType);
                }
            }

            Set<PsqlProductsAmountTypes> productAmountTypes = productsAmountTypesRepository.findPsqlProductsAmountTypesByProductId(productDish.getProductId());
            List<ProductAmountType> productAmountTypeList = new ArrayList<>();
            for (PsqlProductsAmountTypes productsAmountType : productAmountTypes) {
                Optional<AmountType> amountType = AmountType.valueOf(productsAmountType.getAmountTypeId());
                if (amountType.isEmpty()) continue;
                productAmountTypeList.add(new ProductAmountType(amountType.get(), productsAmountType.getGrams()));
            }

            // TODO: Sort Amount Types by its priority
            productDishType.setAmountTypes(productAmountTypeList);

            productList.add(productDishType);
        }

        if (psqlDish.getFoodTypeId() != null) {
            Optional<PsqlFoodType> foodType = this.foodTypeRepository.findById(psqlDish.getFoodTypeId());
            foodType.ifPresent(psqlFoodType -> dishType.setFoodType(FoodType.valueOf(psqlFoodType.getName())));
        }

        dishType.setProducts(productList);

        return dishType;
    }

}
