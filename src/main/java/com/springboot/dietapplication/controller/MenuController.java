package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.menu.*;
import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.model.patient.Patient;
import com.springboot.dietapplication.model.properties.FoodProperties;
import com.springboot.dietapplication.repository.*;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/menus")
public class MenuController {
    private final MenuRepository menuRepository;
    private final WeekMealRepository weekMealRepository;
    private final DayMealRepository dayMealRepository;
    private final MeasurementRepository measurementRepository;
    private final PatientRepository patientRepository;

    public MenuController(MenuRepository menuRepository, WeekMealRepository weekMealRepository,
                          DayMealRepository dayMealRepository, MeasurementRepository measurementRepository,
                          PatientRepository patientRepository) {
        this.menuRepository = menuRepository;
        this.weekMealRepository = weekMealRepository;
        this.dayMealRepository = dayMealRepository;
        this.measurementRepository = measurementRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public List<Menu> getAll() {
        return this.menuRepository.findAll();
    }

    @GetMapping(path = "/{menuId}")
    public Optional<Menu> getMenuById(@PathVariable("menuId") String menuId) {
        return this.menuRepository.findById(menuId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<Menu> getMenusByPatientId(@PathVariable("patientId") String patientId) {
        return this.menuRepository.findByPatientDocRefId(patientId);
    }
    
    @PostMapping(produces = "application/json")
    ResponseEntity<Menu> insertMenu(@RequestBody MenuType menuType) throws NoSuchFieldException {
        Menu menu = new Menu();
        menuRepository.save(menu);

        DateTime dateTime = new DateTime(menuType.getStartDate());
        DateTime endDate = dateTime.plusWeeks(menuType.getWeekCount()).minusDays(1);

        menu.setStartDate(menuType.getStartDate());
        menu.setEndDate(endDate.toString());
        menu.setMealTypes(menuType.getMealTypes());
        menu.setActivityLevel(menuType.getActivityLevel());

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(menuType.getMeasurementId());
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            Optional<Patient> optionalPatient = patientRepository.findById(menuType.getPatientId());
            if (optionalPatient.isPresent()) {
                Patient patient = optionalPatient.get();
                menu.setPatientDocRef(DocRef.fromDoc(patient));
                float weight = measurement.getBodyWeight();
                float activityLevel = menu.getActivityLevel();
                int PPM = calculatePPM(patient, weight, activityLevel);
                FoodProperties foodProperties = calculateBasicFoodProperties(PPM);
                menu.setFoodProperties(foodProperties);
            }
            menu.setMeasurementDocRef(DocRef.fromDoc(measurement));
        }

        DateTime actualDate = dateTime;
        List<String> weekMealList = new ArrayList<>();
        for (int i = 0; i < menuType.getWeekCount(); i++) {
            WeekMeal weekMeal = new WeekMeal();
            weekMealRepository.save(weekMeal);
            List<String> dayMealList = new ArrayList<>();
            for (DayType dayType : DayType.values()) {
                DayMeal dayMeal = new DayMeal();
                dayMeal.setDayType(dayType);
                dayMeal.setDate(actualDate.toString());
                dayMeal.setMenuDocRef(DocRef.fromDoc(menu));
                dayMeal.setWeekMealDocRef(DocRef.fromDoc(weekMeal));
                dayMealRepository.save(dayMeal);
                dayMealList.add(dayMeal.getId());
                actualDate = actualDate.plusDays(1);
            }
            weekMeal.setDayMealList(dayMealList);
            weekMeal.setMenuDocRef(DocRef.fromDoc(menu));
            weekMealRepository.save(weekMeal);
            weekMealList.add(weekMeal.getId());
        }
        menu.setWeekMealList(weekMealList);
        menuRepository.save(menu);

        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{menuId}", produces = "application/json")
    ResponseEntity<Menu> updateMenu(@RequestBody Menu menu) {
        menuRepository.save(menu);
        return ResponseEntity.ok().body(menu);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Menu> deleteMenu(@PathVariable String id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private int calculatePPM(Patient patient, float weight, float activityLevel) {
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

    private FoodProperties calculateBasicFoodProperties(int kcal) {
        FoodProperties foodProperties = new FoodProperties();
        foodProperties.setEnergyValue(kcal);

        float proteins = (0.1f * kcal) / 4.0f;
        foodProperties.setProteins(proteins);

        float fats = (0.3f * kcal) / 9.0f;
        foodProperties.setFats(fats);

        float carbohydrates = (0.6f * kcal) / 4.0f;
        foodProperties.setCarbohydrates(carbohydrates);

        return foodProperties;
    }
}
