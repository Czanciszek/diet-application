package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.mongo.patient.MongoEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoEmployeeRepository extends MongoRepository<MongoEmployee, String> {
}
