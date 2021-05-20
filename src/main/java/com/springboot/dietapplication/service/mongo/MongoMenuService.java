package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoDayMeal;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.patient.MongoMeasurement;
import com.springboot.dietapplication.model.mongo.patient.MongoPatient;
import com.springboot.dietapplication.model.type.DayType;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
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
    private final MongoWeekMealRepository weekMealRepository;
    private final MongoDayMealRepository dayMealRepository;
    private final MongoMeasurementRepository measurementRepository;
    private final MongoPatientRepository patientRepository;

    public MongoMenuService(MongoMenuRepository menuRepository, MongoWeekMealRepository weekMealRepository,
                            MongoDayMealRepository dayMealRepository, MongoMeasurementRepository measurementRepository,
                            MongoPatientRepository patientRepository) {
        this.menuRepository = menuRepository;
        this.weekMealRepository = weekMealRepository;
        this.dayMealRepository = dayMealRepository;
        this.measurementRepository = measurementRepository;
        this.patientRepository = patientRepository;
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
        MongoMenu menu = new MongoMenu();
        menuRepository.save(menu);

        DateTime dateTime = new DateTime(menuType.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuType.getWeekCount()).minusDays(1);

        menu.setStartDate(menuType.getStartDate());
        menu.setEndDate(endDate.toString());
        menu.setMealTypes(menuType.getMealTypes());
        menu.setActivityLevel(menuType.getActivityLevel());

        Optional<MongoMeasurement> optionalMeasurement = measurementRepository.findById(menuType.getMeasurementId());
        if (optionalMeasurement.isPresent()) {
            MongoMeasurement measurement = optionalMeasurement.get();
            Optional<MongoPatient> optionalPatient = patientRepository.findById(menuType.getPatientId());
            if (optionalPatient.isPresent()) {
                MongoPatient patient = optionalPatient.get();
                menu.setPatientId(patient.getId());
                float weight = measurement.getBodyWeight();
                float activityLevel = menu.getActivityLevel();
                int PPM = calculatePPM(patient, weight, activityLevel);
                FoodPropertiesType foodPropertiesType = calculateBasicFoodProperties(PPM);
                menu.setFoodProperties(foodPropertiesType);
            }
            menu.setMeasurementId(measurement.getId());
        }

        DateTime actualDate = dateTime;
        List<String> weekMealList = new ArrayList<>();
        for (int i = 0; i < menuType.getWeekCount(); i++) {
            MongoWeekMeal weekMeal = new MongoWeekMeal();
            weekMealRepository.save(weekMeal);
            List<String> dayMealList = new ArrayList<>();
            for (DayType dayType : DayType.values()) {
                MongoDayMeal dayMeal = new MongoDayMeal();
                dayMeal.setDayType(dayType);
                dayMeal.setDate(actualDate.toString());
                dayMeal.setWeekMealId(weekMeal.getId());
                dayMealRepository.save(dayMeal);
                dayMealList.add(dayMeal.getId());
                actualDate = actualDate.plusDays(1);
            }
            weekMeal.setDayMealList(dayMealList);
            weekMeal.setMenuId(menu.getId());
            weekMealRepository.save(weekMeal);
            weekMealList.add(weekMeal.getId());
        }
        menu.setWeekMealList(weekMealList);
        menuRepository.save(menu);

        return ResponseEntity.ok().body(menu);
    }

    public ResponseEntity<MongoMenu> delete(String id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private int calculatePPM(MongoPatient patient, float weight, float activityLevel) {
        float ppmBase = (patient.isSex()) ? 655.1f : 66.5f;
        float weightFactor = (patient.isSex()) ? 9.563f : 13.75f;
        float heightFactor = (patient.isSex()) ? 1.85f : 5.003f;
        float ageFactor = (patient.isSex()) ? 4.676f : 6.775f;

        DateTime birthDateTime = new DateTime(patient.getBirthDate());
        DateTime currentTime = new DateTime();
        int age = currentTime.getYear() - birthDateTime.getYear();
        float height = patient.getBodyHeight();

        float ppm = ppmBase +
                weightFactor * weight +
                heightFactor * height +
                ageFactor * age;

        return (int) (ppm * activityLevel);
    }

    private FoodPropertiesType calculateBasicFoodProperties(int kcal) {
        FoodPropertiesType foodPropertiesType = new FoodPropertiesType();
        foodPropertiesType.setEnergyValue(kcal);

        float proteins = (0.1f * kcal) / 4.0f;
        foodPropertiesType.setProteins(proteins);

        float fats = (0.3f * kcal) / 9.0f;
        foodPropertiesType.setFats(fats);

        float carbohydrates = (0.6f * kcal) / 4.0f;
        foodPropertiesType.setCarbohydrates(carbohydrates);

        return foodPropertiesType;
    }
}
