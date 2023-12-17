package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoWeekMenuRepository extends MongoRepository<MongoWeekMenu, String> {

    List<MongoWeekMenu> findByMenuId(String menuId);

}
