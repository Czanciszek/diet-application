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

    @Autowired MealRepository mealRepository;
    @Autowired FoodTypeRepository foodTypeRepository;
    @Autowired ProductMealRepository productMealRepository;
    @Autowired AmountTypeRepository amountTypeRepository;

    @Autowired WeekMealService weekMealService;

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

    public List<MealType> getMealsByWeekMealId(String weekMealId) {
        WeekMealType weekMealType = this.weekMealService.getWeekMealById(Long.parseLong(weekMealId));
        return getMealsByDayMealList(weekMealType.getDayMealList());
    }

    public List<MealType> getMealsByDayMealList(List<String> dayMealList) {
        List<MealType> mealList = new ArrayList<>();
        for (String dayMealId: dayMealList) {
            mealList.addAll(getMealsByDayMealId(Long.parseLong(dayMealId)));
        }
        return mealList;
    }

    public MealType insert(MealType meal) {
        PsqlMeal psqlMeal = new PsqlMeal(meal);

        PsqlFoodType psqlFoodType = this.foodTypeRepository.getPsqlFoodTypeByName(meal.getFoodType().toString());
        psqlMeal.setFoodTypeId(psqlFoodType.getId());

        if (psqlMeal.getId() > 0)
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

        meal.setId(String.valueOf(psqlMeal.getId()));
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
        List<PsqlProductMeal> productMeals = this.productMealRepository.findPsqlProductMealsByMealId(Long.parseLong(mealType.getId()));
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
