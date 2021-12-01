package com.springboot.dietapplication.service;

import com.springboot.dietapplication.helper.FoodPropertiesHelper;
import com.springboot.dietapplication.model.psql.menu.*;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.FoodTypeMenuRepository;
import com.springboot.dietapplication.repository.FoodTypeRepository;
import com.springboot.dietapplication.repository.MenuProductsRepository;
import com.springboot.dietapplication.repository.MenuRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired MenuRepository menuRepository;
    @Autowired FoodTypeRepository foodTypeRepository;
    @Autowired FoodTypeMenuRepository foodTypeMenuRepository;
    @Autowired MenuProductsRepository menuProductsRepository;

    @Autowired PatientService patientService;
    @Autowired FoodPropertiesService foodPropertiesService;
    @Autowired WeekMealService weekMealService;
    @Autowired DayMealService dayMealService;

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

    public ResponseEntity<MenuType> insert(MenuSendingType menuSendingType) {
        PsqlMenu menu = new PsqlMenu(menuSendingType);

        DateTime dateTime = new DateTime(menu.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuSendingType.getWeekCount()).minusDays(1);
        menu.setEndDate(endDate.toString());

        PatientType patient = this.patientService.getPatientById(menu.getPatientId());

        FoodPropertiesType foodPropertiesType = FoodPropertiesHelper.calculateFoodPropertiesLimit(
                patient,
                menuSendingType.getPatientWeight(),
                menu.getActivityLevel()
        );

        this.foodPropertiesService.insert(foodPropertiesType);
        menu.setFoodPropertiesId(Long.parseLong(foodPropertiesType.getId()));

        this.menuRepository.save(menu);

        for (FoodType foodType : menuSendingType.getFoodTypes()) {
            PsqlFoodType psqlFoodType = this.foodTypeRepository.getPsqlFoodTypeByName(foodType.name());
            PsqlFoodTypeMenu foodTypeMenu = new PsqlFoodTypeMenu(psqlFoodType.getId(), menu.getId());
            this.foodTypeMenuRepository.save(foodTypeMenu);
        }

        for (int i = 0; i < menuSendingType.getWeekCount(); i++) {
            WeekMealType weekMeal = new WeekMealType();
            weekMeal.setMenuId(String.valueOf(menu.getId()));
            this.weekMealService.insert(weekMeal);
            this.dayMealService.generateDaysForWeek(dateTime, Long.parseLong(weekMeal.getId()));
            dateTime = dateTime.plusWeeks(1);
        }

        this.menuRepository.save(menu);
        return ResponseEntity.ok().body(new MenuType(menu));
    }

    public ResponseEntity<MenuType> delete(long id) {
        this.menuRepository.deleteById(id);
        return ResponseEntity.ok().build();
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

        List<String> weekMealIdList = this.weekMealService.getWeekMealIdList(psqlMenu.getId());
        menuType.setWeekMealList(weekMealIdList);

        FoodPropertiesType foodPropertiesType = this.foodPropertiesService.findById(psqlMenu.getFoodPropertiesId());
        menuType.setFoodPropertiesType(foodPropertiesType);

        return menuType;
    }

    public List<PsqlMenuProduct> menuProductLists(long menuId) {
        return menuProductsRepository.findMenuProducts(menuId);
    }
}
