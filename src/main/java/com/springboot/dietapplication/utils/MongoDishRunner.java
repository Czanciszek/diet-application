package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.repository.mongo.MongoDishRepository;
import com.springboot.dietapplication.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Deprecated(since = "0.1.0", forRemoval = true)
@Service
public class MongoDishRunner {

    @Autowired
    DishService dishService;

    @Autowired
    MongoDishRepository mongoDishRepository;

    public void reloadDishesPSQLtoMongo() {

        mongoDishRepository.deleteAll();

        List<DishType> dishTypeList = dishService.getAll();

        List<MongoDish> dishes = dishTypeList
                .stream()
                .map(MongoDish::new)
                .collect(Collectors.toList());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        dishes.forEach(dish -> {
            dish.setCreationDate(currentDate);
            dish.setUpdateDate(currentDate);
        });

        mongoDishRepository.saveAll(dishes);
    }

}
