package com.springboot.dietapplication.Controller;

import com.springboot.dietapplication.Model.Dish.Dish;
import com.springboot.dietapplication.MongoRepository.DishRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {
    private DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<Dish> getAll() {
        return this.dishRepository.findAll();
    }

}
