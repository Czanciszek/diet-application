package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.type.MongoMealMainScheme;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoMealMainSchemeRepository extends MongoRepository<MongoMealMainScheme, String> {

    List<MongoMealMainScheme> findByPatientId(String patientId);
}
