package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.repository.mongo.MongoDishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoDishService {

    private final MongoDishRepository dishRepository;

    MongoDishService(MongoDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<DishType> getAll() {
        List<MongoDish> dishes = this.dishRepository.findAll();
        return convertMongoDishListToDishTypes(dishes);
    }

    public DishType getDishById(String dishId) {
        return new DishType();
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

    private List<DishType> convertMongoDishListToDishTypes(List<MongoDish> dishes) {
        List<DishType> dishTypeList = new ArrayList<>();

        for (MongoDish dish : dishes) {
            DishType productType = new DishType(dish);
            dishTypeList.add(productType);
        }

        return dishTypeList;
    }

}
