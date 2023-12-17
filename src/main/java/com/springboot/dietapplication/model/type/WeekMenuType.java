package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class WeekMenuType implements Serializable {

    @Serial
    private static final long serialVersionUID = -103585575521933971L;

    private String id;

    private Map<String, List<MealType>> meals;

    public WeekMenuType() {}

    public WeekMenuType(MongoWeekMenu mongoWeekMenu) {
        this.id = mongoWeekMenu.getId();

        Map<String, List<MealType>> mealsMap = new HashMap<>();
        for (Map.Entry<String, List<MongoMeal>> entry : mongoWeekMenu.getMeals().entrySet()) {
            List<MealType> mealTypes = entry.getValue().stream().map(MealType::new).collect(Collectors.toList());
            mealsMap.put(entry.getKey(), mealTypes);
        }
        this.meals = mealsMap;
    }
}
