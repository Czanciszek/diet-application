package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.patient.BriefPatient;
import com.springboot.dietapplication.model.psql.menu.*;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.psql.product.PsqlShoppingProduct;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    FoodTypeRepository foodTypeRepository;
    @Autowired
    FoodTypeMenuRepository foodTypeMenuRepository;
    @Autowired
    MenuProductsRepository menuProductsRepository;
    @Autowired
    ShoppingProductRepository shoppingProductRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ProductMealRepository productMealRepository;
    @Autowired
    MealRepository mealRepository;

    @Autowired
    WeekMealService weekMealService;
    @Autowired
    DayMealService dayMealService;

    public List<MenuType> getAll() {
        return new ArrayList<>();
    }

    public MenuType getMenuById(long menuId) {
        return new MenuType();
    }

    public List<MenuType> getMenusByPatientId(long patientId) {
        return new ArrayList<>();
    }

    public List<PsqlMenuProduct> menuProducts(long menuId) {
        return menuProductsRepository.findMenuProducts(menuId);
    }

    public List<PsqlShoppingProduct> getShoppingProductsForMenu(long menuId) {
        return shoppingProductRepository.findShoppingProductsByMenuId(menuId);
    }

    public ResponseEntity<MenuType> insert(NewMenuType newMenuType) {
        PsqlMenu menu = new PsqlMenu(newMenuType);

        DateTime dateTime = new DateTime(menu.getStartDate());
        DateTime endDate = dateTime.plusWeeks(newMenuType.getWeekMenusCount()).minusDays(1);
        menu.setEndDate(endDate.toString());

        this.menuRepository.save(menu);

        saveMenuFoodProperties(newMenuType.getFoodTypes(), menu.getId());

        for (int i = 0; i < newMenuType.getWeekMenusCount(); i++) {
            WeekMealType weekMeal = new WeekMealType();
            weekMeal.setMenuId(menu.getId());
            this.weekMealService.insert(weekMeal);
            this.dayMealService.generateDaysForWeek(dateTime, weekMeal.getId());
            dateTime = dateTime.plusWeeks(1);
        }

        this.menuRepository.save(menu);
        return ResponseEntity.ok().body(new MenuType(menu));
    }

    public ResponseEntity<Void> replaceProductInMenu(Long menuId, ProductReplaceType productReplaceType) {

        String newProductId = productReplaceType.getNewProductId();

//        List<PsqlProductMeal> productMealList = productMealRepository.findPsqlProductMealByMenuIdAndByProductId(menuId, Long.valueOf(productReplaceType.getOldProductId()));
//        productMealList.forEach( product -> {
//            product.setProductId(Long.valueOf(newProductId));
//        });
//        productMealRepository.saveAll(productMealList);
//
//        List<PsqlMeal> mealList = mealRepository.findByName(productReplaceType.getOldProduct().getName());
//        mealList.forEach( meal -> {
//            meal.setName(newProduct.getName());
//        });
//        mealRepository.saveAll(mealList);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<MenuType> update(NewMenuType newMenuType) {
        Long menuTypeId = Long.valueOf(newMenuType.getId());
        PsqlMenu updateMenu = new PsqlMenu(newMenuType);

        Optional<PsqlMenu> currentMenu = this.menuRepository.findById(menuTypeId);
        if (currentMenu.isEmpty()) return null;
        updateMenu.setId(menuTypeId);
        updateMenu.setEndDate(currentMenu.get().getEndDate());

        saveMenuFoodProperties(newMenuType.getFoodTypes(), menuTypeId);

        this.menuRepository.save(updateMenu);
        return ResponseEntity.ok().body(new MenuType(updateMenu));
    }

    public ResponseEntity<MenuType> copy(NewMenuType newMenuType) {

        PsqlMenu originMenu = getCurrentMenuData(Long.parseLong(newMenuType.getId()));
        if (originMenu == null)
            return null;

        PsqlMenu newMenu = new PsqlMenu(newMenuType);

        DateTime dateTime = new DateTime(newMenuType.getStartDate());
        newMenu.setStartDate(dateTime.toString());
        DateTime endDate = dateTime.plusWeeks(newMenuType.getWeekMenusCount()).minusDays(1);
        newMenu.setEndDate(endDate.toString());

        this.menuRepository.save(newMenu);

        saveMenuFoodProperties(newMenuType.getFoodTypes(), newMenu.getId());

        float factor = Math.round((newMenu.getEnergyLimit() / originMenu.getEnergyLimit()) * 100f) / 100f;
        this.weekMealService.copy(originMenu.getId(), newMenu.getId(), factor, newMenu.getStartDate());

        return ResponseEntity.ok().body(new MenuType(newMenu));
    }

    public ResponseEntity<MenuType> delete(long id) {

        this.weekMealService.deleteByMenuId(id);

        this.foodTypeMenuRepository.deletePsqlFoodTypeMenuByMenuId(id);
        this.menuRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    public void updateMenuRecommendations(GenerateMenuType generateMenuType) {
        Optional<PsqlMenu> menu = this.menuRepository.findById(generateMenuType.getMenuId());
        if (menu.isEmpty()) return;
        menu.get().setRecommendations(generateMenuType.getRecommendations());
        this.menuRepository.save(menu.get());
    }

    private PsqlMenu getCurrentMenuData(long menuId) {
        Optional<PsqlMenu> optionalPsqlMenu = this.menuRepository.findById(menuId);
        return optionalPsqlMenu.orElse(null);
    }

    private void saveMenuFoodProperties(List<FoodType> foodTypes, Long menuId) {
        this.foodTypeMenuRepository.deletePsqlFoodTypeMenuByMenuId(menuId);
        for (FoodType foodType : foodTypes) {
            PsqlFoodType psqlFoodType = this.foodTypeRepository.getPsqlFoodTypeByName(foodType.name());
            PsqlFoodTypeMenu foodTypeMenu = new PsqlFoodTypeMenu(psqlFoodType.getId(), menuId);
            this.foodTypeMenuRepository.save(foodTypeMenu);
        }
    }

}
