package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlAmountType;
import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import com.springboot.dietapplication.model.psql.menu.PsqlMeal;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.AmountTypeRepository;
import com.springboot.dietapplication.repository.FoodTypeRepository;
import com.springboot.dietapplication.repository.MealRepository;
import com.springboot.dietapplication.repository.ProductMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;
    @Autowired
    FoodTypeRepository foodTypeRepository;
    @Autowired
    ProductMealRepository productMealRepository;
    @Autowired
    AmountTypeRepository amountTypeRepository;

    @Autowired
    WeekMealService weekMealService;
    @Autowired
    MenuService menuService;

    public List<MealType> getAll() {
        List<PsqlMeal> mealList = this.mealRepository.findAll();
        return convertLists(mealList);
    }

    public MealType getMealById(Long mealId) {
        Optional<PsqlMeal> meal = this.mealRepository.findById(mealId);
        return meal.map(this::convertPsqlDayMealToDayMealType).orElseGet(MealType::new);
    }

    public List<MealType> getMealsByDayMealId(Long dayMealId) {
        List<PsqlMeal> mealList =  this.mealRepository.findByDayMealId(dayMealId);
        return convertLists(mealList);
    }

    public List<MealType> getMealsByWeekMealId(Long weekMealId) {
        WeekMealType weekMealType = this.weekMealService.getWeekMealById(weekMealId);
        return getMealsByDayMealList(weekMealType.getDayMealList());
    }

    public List<MealType> getMealsByMenuId(Long menuId) {
        MenuType menuType = this.menuService.getMenuById(menuId);
        return getMealsByWeekMealList(menuType.getWeekMealList());
    }

    public List<MealType> getMealsByWeekMealList(List<Long> weekMealList) {
        List<MealType> mealList = new ArrayList<>();
        for (Long weekMealId: weekMealList) {
            mealList.addAll(getMealsByWeekMealId(weekMealId));
        }
        return mealList;
    }

    public List<MealType> getMealsByDayMealList(List<Long> dayMealIdList) {
        List<MealType> mealList = new ArrayList<>();
        for (Long dayMealId: dayMealIdList) {
            mealList.addAll(getMealsByDayMealId(dayMealId));
        }
        return mealList;
    }

    public MealType insert(MealType meal, boolean isMealCopied) {
        PsqlMeal psqlMeal = new PsqlMeal(meal);

        PsqlFoodType psqlFoodType = this.foodTypeRepository.getPsqlFoodTypeByName(meal.getFoodType().toString());
        psqlMeal.setFoodTypeId(psqlFoodType.getId());

        if (psqlMeal.getId() != null)
            this.productMealRepository.deletePsqlProductMealsByMealId(psqlMeal.getId());

        this.mealRepository.save(psqlMeal);

        for (ProductDishType productDish : meal.getProductList()) {
            PsqlProductMeal psqlProductMeal = new PsqlProductMeal(productDish);
            psqlProductMeal.setMealId(psqlMeal.getId());

            if (productDish.getAmountType() != null) {
                PsqlAmountType amountType = this.amountTypeRepository.getPsqlAmountTypeByName(productDish.getAmountType().toString());
                psqlProductMeal.setAmountTypeId(amountType.getId());
            } else {
                psqlProductMeal.setAmountTypeId(0L);
            }

            this.productMealRepository.save(psqlProductMeal);
        }

        if (!isMealCopied) {
            psqlMeal.setOriginMealId(psqlMeal.getId());
        }

        this.mealRepository.save(psqlMeal);

        meal.setId(psqlMeal.getId());
        return meal;
    }

    public void delete(Long mealId) {
        this.productMealRepository.deletePsqlProductMealsByMealId(mealId);
        this.mealRepository.deleteById(mealId);
    }

    private List<MealType> convertLists(List<PsqlMeal> mealList) {
        List<MealType> mealTypeList = new ArrayList<>();
        for (PsqlMeal meal : mealList) {
            mealTypeList.add(convertPsqlDayMealToDayMealType(meal));
        }
        return mealTypeList;
    }

    private MealType convertPsqlDayMealToDayMealType(PsqlMeal meal) {
        MealType mealType = new MealType(meal);

        List<ProductDishType> productDishTypeList = new ArrayList<>();
        List<PsqlProductMeal> productMeals = this.productMealRepository.findPsqlProductMealsByMealId(mealType.getId());
        for (PsqlProductMeal productMeal : productMeals) {
            ProductDishType productDishType = new ProductDishType(productMeal);

            if (productMeal.getAmountTypeId() != null) {
                Optional<PsqlAmountType> amountTypeOptional = this.amountTypeRepository.findById(productMeal.getAmountTypeId());
                if (amountTypeOptional.isPresent()) {
                    AmountType amountType = AmountType.valueOf(amountTypeOptional.get().getName());
                    productDishType.setAmountType(amountType);
                }
            }

            productDishTypeList.add(productDishType);
        }
        mealType.setProductList(productDishTypeList);

        Optional<PsqlFoodType> psqlFoodType = this.foodTypeRepository.findById(meal.getFoodTypeId());
        psqlFoodType.ifPresent(foodType -> mealType.setFoodType(FoodType.valueOf(foodType.getName())));

        return mealType;
    }
}
