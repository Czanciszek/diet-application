package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.helper.FoodPropertiesHelper;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.repository.mongo.*;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoMenuService {

    private final MongoMenuRepository menuRepository;

    private final MongoWeekMealService weekMealService;
    private final MongoDayMealService dayMealService;
    private final MongoMeasurementService measurementService;
    private final MongoPatientService patientService;

    public MongoMenuService(MongoMenuRepository menuRepository,
                            MongoWeekMealService weekMealService,
                            MongoDayMealService dayMealService,
                            MongoMeasurementService measurementService,
                            MongoPatientService patientService) {
        this.menuRepository = menuRepository;
        this.weekMealService = weekMealService;
        this.dayMealService = dayMealService;
        this.measurementService = measurementService;
        this.patientService = patientService;
    }

    public List<MenuType> getAll() {
        List<MongoMenu> mongoMenuList = this.menuRepository.findAll();
        return convertMongoMenuToMenuType(mongoMenuList);
    }

    public MenuType getMenuById(String menuId) {
        Optional<MongoMenu> menu = this.menuRepository.findById(menuId);
        return menu.map(MenuType::new).orElseGet(MenuType::new);
    }

    public List<MenuType> getMenusByPatientId(String patientId) {
        List<MongoMenu> mongoMenuList = this.menuRepository.findByPatientId(patientId);
        return convertMongoMenuToMenuType(mongoMenuList);
    }

    public ResponseEntity<MenuType> insert(MenuSendingType menuSendingType) {
        MongoMenu menu = new MongoMenu(menuSendingType);
        menuRepository.save(menu);

        DateTime dateTime = new DateTime(menu.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuSendingType.getWeekCount()).minusDays(1);
        menu.setEndDate(endDate.toString());

        MeasurementType measurement = this.measurementService.getMeasurementById(menu.getMeasurementId());
        PatientType patient = this.patientService.getPatientById(menuSendingType.getPatientId());

        FoodPropertiesType foodPropertiesType = FoodPropertiesHelper.calculateFoodPropertiesLimit(
                patient,
                measurement.getBodyWeight(),
                menu.getActivityLevel()
        );

        menu.setFoodProperties(foodPropertiesType);

        List<String> weekMealList = new ArrayList<>();
        for (int i = 0; i < menuSendingType.getWeekCount(); i++) {
            WeekMealType weekMeal = new WeekMealType();
            this.weekMealService.insert(weekMeal);

            List<String> dayMealIdList = this.dayMealService.generateDaysForWeek(dateTime, weekMeal.getId());

            weekMeal.setDayMealList(dayMealIdList);
            weekMeal.setMenuId(menu.getId());
            this.weekMealService.insert(weekMeal);

            weekMealList.add(weekMeal.getId());
            dateTime = dateTime.plusWeeks(1);
        }

        menu.setWeekMealList(weekMealList);
        menuRepository.save(menu);


        return ResponseEntity.ok().body(new MenuType(menu));
    }

    public ResponseEntity<MenuType> delete(String id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private List<MenuType> convertMongoMenuToMenuType(List<MongoMenu> mongoMenus) {
        List<MenuType> menuTypeList = new ArrayList<>();

        for (MongoMenu mongoMenu : mongoMenus) {
            menuTypeList.add(new MenuType(mongoMenu));
        }

        return menuTypeList;
    }

}
