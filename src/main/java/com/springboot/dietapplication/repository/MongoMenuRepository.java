package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoMenuRepository extends MongoRepository<MongoMenu, String> {

    List<MongoMenu> findByPatientId(String patientId);
}
