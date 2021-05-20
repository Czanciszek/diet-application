package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.helper.FoodPropertiesHelper;
import com.springboot.dietapplication.helper.PatientHelper;
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

    public List<MongoMenu> getAll() {
        return this.menuRepository.findAll();
    }

    public MongoMenu getMenuById(String menuId) {
        Optional<MongoMenu> measurement = this.menuRepository.findById(menuId);
        return measurement.orElseGet(MongoMenu::new);
    }

    public List<MongoMenu> getMenusByPatientId(String patientId) {
        return this.menuRepository.findByPatientId(patientId);
    }

    public ResponseEntity<MongoMenu> insert(MenuType menuType) {
        MongoMenu menu = new MongoMenu(menuType);
        menuRepository.save(menu);

        DateTime dateTime = new DateTime(menu.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuType.getWeekCount()).minusDays(1);
        menu.setEndDate(endDate.toString());

        MeasurementType measurement = this.measurementService.getMeasurementById(menu.getMeasurementId());

        PatientType patient = this.patientService.getPatientById(menuType.getPatientId());
        float weight = measurement.getBodyWeight();
        float activityLevel = menu.getActivityLevel();

        int PPM = PatientHelper.calculatePPM(patient, weight, activityLevel);
        FoodPropertiesType foodPropertiesType = FoodPropertiesHelper.calculateBasicFoodProperties(PPM);
        menu.setFoodProperties(foodPropertiesType);

        List<String> weekMealList = new ArrayList<>();
        for (int i = 0; i < menuType.getWeekCount(); i++) {
            MongoWeekMeal weekMeal = new MongoWeekMeal();
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

        return ResponseEntity.ok().body(menu);
    }

    public ResponseEntity<MongoMenu> delete(String id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
