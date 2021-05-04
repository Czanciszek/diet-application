package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.properties.PsqlFoodProperties;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.repository.psql.PsqlFoodPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PsqlFoodPropertiesService {

    @Autowired
    PsqlFoodPropertiesRepository foodPropertiesRepository;

    public FoodPropertiesType findById(Long foodPropertiesId) {
        Optional<PsqlFoodProperties> foodProperties = this.foodPropertiesRepository.findById(foodPropertiesId);
        return foodProperties.map(FoodPropertiesType::new).orElseGet(FoodPropertiesType::new);
    }

    public void insert(FoodPropertiesType foodPropertiesType) {
        PsqlFoodProperties foodProperties = new PsqlFoodProperties(foodPropertiesType);
        this.foodPropertiesRepository.save(foodProperties);
        foodPropertiesType.setId(String.valueOf(foodProperties.getId()));
    }

    public void delete(Long id) {
        this.foodPropertiesRepository.deleteById(id);
    }
}
