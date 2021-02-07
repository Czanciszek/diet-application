package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.menu.*;
import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.model.patient.Patient;
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

        Optional<Measurement> measurement = measurementRepository.findById(menuType.getMeasurementId());
        measurement.ifPresent(m -> menu.setMeasurementDocRef(DocRef.fromDoc(m)));

        Optional<Patient> patient = patientRepository.findById(menuType.getPatientId());
        patient.ifPresent(p -> menu.setPatientDocRef(DocRef.fromDoc(p)));

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
}
