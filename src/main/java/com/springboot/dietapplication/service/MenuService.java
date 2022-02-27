package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.*;
import com.springboot.dietapplication.model.psql.product.PsqlShoppingProduct;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    WeekMealService weekMealService;
    @Autowired
    DayMealService dayMealService;

    public List<MenuType> getAll() {
        List<PsqlMenu> psqlMenuList = this.menuRepository.findAll();
        return convertLists(psqlMenuList);
    }

    public MenuType getMenuById(long menuId) {
        Optional<PsqlMenu> menu = this.menuRepository.findById(menuId);
        return menu.map(this::convertPsqlMenuToMenuType).orElseGet(MenuType::new);
    }

    public List<MenuType> getMenusByPatientId(long patientId) {
        List<PsqlMenu> psqlMenuList = this.menuRepository.findPsqlMenusByPatientId(patientId);
        return convertLists(psqlMenuList);
    }

    public List<PsqlMenuProduct> menuProductLists(long menuId) {
        return menuProductsRepository.findMenuProducts(menuId);
    }
    public List<PsqlShoppingProduct> getShoppingProductsForMenu(long menuId) {
        return shoppingProductRepository.findShoppingProductsByMenuId(menuId);
    }

    public ResponseEntity<MenuType> insert(MenuSendingType menuSendingType) {
        PsqlMenu menu = new PsqlMenu(menuSendingType);

        DateTime dateTime = new DateTime(menu.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuSendingType.getWeekCount()).minusDays(1);
        menu.setEndDate(endDate.toString());

        this.menuRepository.save(menu);

        saveMenuFoodProperties(menuSendingType.getFoodTypes(), menu.getId());

        for (int i = 0; i < menuSendingType.getWeekCount(); i++) {
            WeekMealType weekMeal = new WeekMealType();
            weekMeal.setMenuId(menu.getId());
            this.weekMealService.insert(weekMeal);
            this.dayMealService.generateDaysForWeek(dateTime, weekMeal.getId());
            dateTime = dateTime.plusWeeks(1);
        }

        this.menuRepository.save(menu);
        return ResponseEntity.ok().body(new MenuType(menu));
    }

    public ResponseEntity<MenuType> update(MenuSendingType menuSendingType) {
        PsqlMenu updateMenu = new PsqlMenu(menuSendingType);

        Optional<PsqlMenu> currentMenu = this.menuRepository.findById(menuSendingType.getId());
        if (!currentMenu.isPresent()) return null;
        updateMenu.setId(menuSendingType.getId());
        updateMenu.setEndDate(currentMenu.get().getEndDate());

        saveMenuFoodProperties(menuSendingType.getFoodTypes(), menuSendingType.getId());

        this.menuRepository.save(updateMenu);
        return ResponseEntity.ok().body(new MenuType(updateMenu));
    }

    public ResponseEntity<MenuType> copy(MenuSendingType menuSendingType) {

        PsqlMenu originMenu = getCurrentMenuData(menuSendingType.getId());
        if (originMenu == null)
            return null;

        PsqlMenu newMenu = new PsqlMenu(menuSendingType);
        newMenu.setStartDate(originMenu.getStartDate());
        newMenu.setEndDate(originMenu.getEndDate());

        this.menuRepository.save(newMenu);

        saveMenuFoodProperties(menuSendingType.getFoodTypes(), newMenu.getId());

        float factor = Math.round((newMenu.getEnergyLimit() / originMenu.getEnergyLimit()) * 100f) / 100f;
        this.weekMealService.copy(originMenu.getId(), newMenu.getId(), factor);

        return ResponseEntity.ok().body(new MenuType(newMenu));
    }

    public ResponseEntity<MenuType> delete(long id) {

        this.weekMealService.deleteByMenuId(id);

        this.foodTypeMenuRepository.deletePsqlFoodTypeMenuByMenuId(id);
        this.menuRepository.deleteById(id);

        return ResponseEntity.ok().build();
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

    private List<MenuType> convertLists(List<PsqlMenu> psqlMenuList) {
        List<MenuType> menuList = new ArrayList<>();

        for (PsqlMenu menu : psqlMenuList) {
            menuList.add(convertPsqlMenuToMenuType(menu));
        }

        return menuList;
    }

    private MenuType convertPsqlMenuToMenuType(PsqlMenu psqlMenu) {
        MenuType menuType = new MenuType(psqlMenu);

        List<FoodType> foodTypeList = new ArrayList<>();
        List<PsqlFoodTypeMenu> psqlFoodTypeList = this.foodTypeMenuRepository.findPsqlFoodTypeMenuByMenuId(psqlMenu.getId());
        for (PsqlFoodTypeMenu foodTypeMenu : psqlFoodTypeList) {
            Optional<PsqlFoodType> foodType = this.foodTypeRepository.findById(foodTypeMenu.getFoodTypeId());
            foodType.ifPresent(psqlFoodType -> foodTypeList.add(FoodType.valueOf(psqlFoodType.getName())));
        }
        menuType.setFoodTypes(foodTypeList);

        List<Long> weekMealIdList = this.weekMealService.getWeekMealIdList(psqlMenu.getId());
        menuType.setWeekMealList(weekMealIdList);

        List<DayMealType> dayMealTypeList = new ArrayList<>();
        for (Long weekMealId: weekMealIdList)
            dayMealTypeList.addAll(this.dayMealService.getDayMealByWeekMealId(weekMealId));

        menuType.setDayMealTypeList(dayMealTypeList);

        return menuType;
    }

}
