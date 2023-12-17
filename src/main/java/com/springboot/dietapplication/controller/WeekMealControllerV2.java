package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.WeekMenuType;
import com.springboot.dietapplication.service.WeekMealServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v2/weekmeals")
public class WeekMealControllerV2 {

    @Autowired
    WeekMealServiceV2 weekMealService;

    @GetMapping(path = "/menu/{menuId}")
    public ResponseEntity<?> getAllByMenuId(@PathVariable("menuId") String menuId) {
        try {
            List<WeekMenuType> menuList = this.weekMealService.getWeekMealsByMenuId(menuId);
            return ResponseEntity.ok(menuList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        try {
            this.weekMealService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }

}
