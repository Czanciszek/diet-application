package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.*;
import com.springboot.dietapplication.model.psql.product.PsqlProductsAmountTypes;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

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
    ProductsAmountTypesRepository productsAmountTypesRepository;

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

            if (meal.getIsProduct() == 1) { break; }
        }

        if (!isMealCopied) {
            psqlMeal.setOriginMealId(psqlMeal.getId());
        }

        this.mealRepository.save(psqlMeal);

        meal.setId(psqlMeal.getId());
        return meal;
    }

    public void copy(long originDayMealId, long newDayMealId, float factor) {
        List<PsqlMeal> mealList = this.mealRepository.findByDayMealId(originDayMealId);
        for (PsqlMeal originMeal : mealList) {
            PsqlMeal newMeal = new PsqlMeal(originMeal);
            newMeal.setDayMealId(newDayMealId);
            newMeal.setGrams(originMeal.getGrams() * factor);
            mealRepository.save(newMeal);

            if (originMeal.getId().equals(originMeal.getOriginMealId())) {
                newMeal.setOriginMealId(newMeal.getId());
                mealRepository.save(newMeal);
            }

            List<PsqlProductMeal> productMealList = this.productMealRepository.findPsqlProductMealsByMealId(originMeal.getId());
            for (PsqlProductMeal productMeal : productMealList) {
                PsqlProductMeal newProductMeal = new PsqlProductMeal(productMeal);
                newProductMeal.setMealId(newMeal.getId());
                newProductMeal.setGrams(productMeal.getGrams() * factor);
                productMealRepository.save(newProductMeal);
            }
        }
    }

    public void delete(Long mealId) {
        this.productMealRepository.deletePsqlProductMealsByMealId(mealId);
        this.mealRepository.deleteById(mealId);
    }

    public void deleteByDayMealId(Long id) {
        List<PsqlMeal> meals = mealRepository.findByDayMealId(id);
        for (PsqlMeal meal : meals) {
            delete(meal.getId());
        }
    }

    private List<MealType> convertLists(List<PsqlMeal> mealList) {
        List<MealType> mealTypeList = new ArrayList<>();
        for (PsqlMeal meal : mealList) {
            mealTypeList.add(convertPsqlDayMealToDayMealType(meal));
        }

        mealTypeList.sort(new FoodTypeComparator());

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

            Set<PsqlProductsAmountTypes> productAmountTypes = productsAmountTypesRepository.findPsqlProductsAmountTypesByProductId(productMeal.getProductId());
            List<ProductAmountType> productAmountTypeList = new ArrayList<>();
            for (PsqlProductsAmountTypes productsAmountType : productAmountTypes) {
                Optional<AmountType> amountType = AmountType.valueOf(productsAmountType.getAmountTypeId());
                if (!amountType.isPresent()) continue;
                productAmountTypeList.add(new ProductAmountType(amountType.get(), productsAmountType.getGrams()));
            }

            // TODO: Sort Amount Types by its priority
            productDishType.setAmountTypes(productAmountTypeList);

            productDishTypeList.add(productDishType);
        }
        mealType.setProductList(productDishTypeList);

        Optional<PsqlFoodType> psqlFoodType = this.foodTypeRepository.findById(meal.getFoodTypeId());
        psqlFoodType.ifPresent(foodType -> mealType.setFoodType(FoodType.valueOf(foodType.getName())));

        return mealType;
    }
}

class FoodTypeComparator implements Comparator<MealType> {

    @Override
    public int compare(MealType o1, MealType o2) {
        return Integer.compare(getAssignedValue(o1), getAssignedValue(o2));
    }

    int getAssignedValue(MealType mealType) {
        switch (mealType.getFoodType()) {
            case PRE_BREAKFAST:
                return 0;
            case BREAKFAST:
                return 1;
            case BRUNCH:
                return 2;
            case SNACK:
                return 3;
            case DINNER:
                return 4;
            case TEA:
                return 5;
            case SUPPER:
                return 6;
            case PRE_WORKOUT:
                return 7;
            case POST_WORKOUT:
                return 8;
            case OVERNIGHT:
                return 9;
        }

        return Integer.MAX_VALUE;
    }

}