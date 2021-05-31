package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.repository.mongo.MongoDishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoDishService {

    private final MongoDishRepository dishRepository;

    MongoDishService(MongoDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<DishType> getAll() {
        List<MongoDish> dishes = this.dishRepository.findAll();
        return convertLists(dishes);
    }

    public DishType getDishById(String dishId) {
        Optional<MongoDish> dish = this.dishRepository.findById(dishId);
        return dish.map(this::convertMongoDishToDishType).orElseGet(DishType::new);
    }

    public ResponseEntity<DishType> insert(DishType dish) {
        MongoDish mongoDish = new MongoDish(dish);
        this.dishRepository.save(mongoDish);
        dish.setId(mongoDish.getId());
        return ResponseEntity.ok().body(dish);
    }

    public ResponseEntity<Void> delete(String id) {
        dishRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private List<DishType> convertLists(List<MongoDish> mongoDishes) {
        List<DishType> dishTypeList = new ArrayList<>();
        for (MongoDish dish : mongoDishes) {
            dishTypeList.add(convertMongoDishToDishType(dish));
        }
        return dishTypeList;
    }

    private DishType convertMongoDishToDishType(MongoDish dish) {
        return new DishType(dish);
    }

}
