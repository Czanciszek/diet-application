package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.helper.FoodPropertiesHelper;
import com.springboot.dietapplication.model.psql.menu.*;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.psql.PsqlMealTypeMenuRepository;
import com.springboot.dietapplication.repository.psql.PsqlMealTypeRepository;
import com.springboot.dietapplication.repository.psql.PsqlMenuRepository;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PsqlMenuService {

    private final PsqlMenuRepository menuRepository;
    private final PsqlMealTypeRepository mealTypeRepository;
    private final PsqlMealTypeMenuRepository mealTypeMenuRepository;

    private final PsqlMeasurementService measurementService;
    private final PsqlPatientService patientService;
    private final PsqlFoodPropertiesService foodPropertiesService;
    private final PsqlWeekMealService weekMealService;
    private final PsqlDayMealService dayMealService;

    public PsqlMenuService(PsqlMenuRepository menuRepository,
                           PsqlMealTypeRepository mealTypeRepository,
                           PsqlMealTypeMenuRepository mealTypeMenuRepository,
                           PsqlMeasurementService measurementService,
                           PsqlPatientService patientService,
                           PsqlFoodPropertiesService foodPropertiesService,
                           PsqlWeekMealService weekMealService,
                           PsqlDayMealService dayMealService) {
        this.menuRepository = menuRepository;
        this.mealTypeRepository = mealTypeRepository;
        this.mealTypeMenuRepository = mealTypeMenuRepository;
        this.measurementService = measurementService;
        this.patientService = patientService;
        this.foodPropertiesService = foodPropertiesService;
        this.weekMealService = weekMealService;
        this.dayMealService = dayMealService;
    }

    public List<MenuType> getAll() {
        List<PsqlMenu> psqlMenuList = this.menuRepository.findAll();
        return convertLists(psqlMenuList);
    }

    public MenuType getMenuById(long menuId) {
        Optional<PsqlMenu> menu = this.menuRepository.findById(menuId);
        return menu.map(this::convertMongoMenuToMenuType).orElseGet(MenuType::new);
    }

    public List<MenuType> getMenusByPatientId(long patientId) {
        List<PsqlMenu> mongoMenuList = this.menuRepository.findPsqlMenusByPatientId(patientId);
        return convertLists(mongoMenuList);
    }

    public ResponseEntity<MenuType> insert(MenuSendingType menuSendingType) {
        PsqlMenu menu = new PsqlMenu(menuSendingType);

        DateTime dateTime = new DateTime(menu.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuSendingType.getWeekCount()).minusDays(1);
        menu.setEndDate(endDate.toString());

        MeasurementType measurement = this.measurementService.getMeasurementById(menu.getMeasurementId());
        PatientType patient = this.patientService.getPatientById(menu.getPatientId());

        FoodPropertiesType foodPropertiesType = FoodPropertiesHelper.calculateFoodPropertiesLimit(
                patient,
                measurement.getBodyWeight(),
                menu.getActivityLevel()
        );

        this.foodPropertiesService.insert(foodPropertiesType);
        menu.setFoodPropertiesId(Long.parseLong(foodPropertiesType.getId()));

        this.menuRepository.save(menu);

        for (MealType mealType : menuSendingType.getMealTypes()) {
            PsqlMealType psqlMealType = this.mealTypeRepository.getPsqlMealTypeByName(mealType.name());
            PsqlMealTypeMenu mealTypeMenu = new PsqlMealTypeMenu(psqlMealType.getId(), menu.getId());
            this.mealTypeMenuRepository.save(mealTypeMenu);
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
            menuList.add(convertMongoMenuToMenuType(menu));
        }

        return menuList;
    }

    private MenuType convertMongoMenuToMenuType(PsqlMenu psqlMenu) {
        MenuType menuType = new MenuType(psqlMenu);

        List<MealType> mealTypeList = new ArrayList<>();
        List<PsqlMealTypeMenu> menuTypeList = this.mealTypeMenuRepository.findPsqlMealTypeMenuByMenuId(psqlMenu.getId());
        for (PsqlMealTypeMenu mealTypeMenu : menuTypeList) {
            Optional<PsqlMealType> mealType = this.mealTypeRepository.findById(mealTypeMenu.getMealTypeId());
            mealType.ifPresent(psqlMealType -> mealTypeList.add(MealType.valueOf(psqlMealType.getName())));
        }
        menuType.setMealTypes(mealTypeList);

        List<String> weekMealIdList = this.weekMealService.getWeekMealIdList(psqlMenu.getId());
        menuType.setWeekMealList(weekMealIdList);

        FoodPropertiesType foodPropertiesType = this.foodPropertiesService.findById(psqlMenu.getFoodPropertiesId());
        menuType.setFoodProperties(foodPropertiesType);

        return menuType;
    }
}
