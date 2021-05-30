package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import com.springboot.dietapplication.model.psql.menu.PsqlMeal;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.model.type.ProductDishType;
import com.springboot.dietapplication.repository.psql.PsqlFoodTypeRepository;
import com.springboot.dietapplication.repository.psql.PsqlMealRepository;
import com.springboot.dietapplication.repository.psql.PsqlProductMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PsqlMealService {

    @Autowired
    private final PsqlMealRepository mealRepository;

    @Autowired
    private final PsqlFoodTypeRepository foodTypeRepository;

    @Autowired
    private final PsqlProductMealRepository productMealRepository;

    public PsqlMealService(PsqlMealRepository mealRepository, PsqlFoodTypeRepository foodTypeRepository, PsqlProductMealRepository productMealRepository) {
        this.mealRepository = mealRepository;
        this.foodTypeRepository = foodTypeRepository;
        this.productMealRepository = productMealRepository;
    }

    public List<MealType> getAll() {
        List<PsqlMeal> mealList = this.mealRepository.findAll();
        return convertLists(mealList);
    }

    public MealType getMealById(Long mealId) {
        Optional<PsqlMeal> meal = this.mealRepository.findById(mealId);
        return meal.map(this::convertPsqlDayMealToDayMealType).orElseGet(MealType::new);
    }

    public List<MealType> getMealsByDayMealList(List<String> dayMealList) {
        List<PsqlMeal> mealList = new ArrayList<>();
        for (String dayMealId: dayMealList) {
            mealList.addAll(mealRepository.findByDayMealId(Long.parseLong(dayMealId)));
        }
        return convertLists(mealList);
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
            productDishTypeList.add(productDishType);
        }
        mealType.setProductList(productDishTypeList);

        Optional<PsqlFoodType> psqlFoodType = this.foodTypeRepository.findById(meal.getFoodTypeId());
        psqlFoodType.ifPresent(foodType -> mealType.setFoodType(FoodType.valueOf(foodType.getName())));

        return mealType;
    }
}
