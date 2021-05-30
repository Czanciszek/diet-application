package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.service.psql.PsqlDayMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/psql/daymeals")
public class PsqlDayMealController {

    private final PsqlDayMealService dayMealService;

    public PsqlDayMealController(PsqlDayMealService dayMealService) {
        this.dayMealService = dayMealService;
    }

    @GetMapping(path = "/list/{weekMealId}")
    public List<DayMealType> getDayMealByWeekMealId(@PathVariable("weekMealId") String weekMealId) {
        return this.dayMealService.getDayMealByWeekMealId(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DayMealType> insert(@RequestBody DayMealType dayMeal) {
        return this.dayMealService.insert(dayMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<DayMealType> delete(@PathVariable Long id) {
        return this.dayMealService.delete(id);
    }

}
