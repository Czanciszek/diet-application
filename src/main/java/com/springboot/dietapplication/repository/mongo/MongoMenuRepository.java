package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoMenuRepository extends MongoRepository<MongoMenu, String> {

    List<MongoMenu> findByPatientId(String id);

}
