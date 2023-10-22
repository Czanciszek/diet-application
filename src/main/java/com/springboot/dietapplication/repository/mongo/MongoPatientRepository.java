package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.MongoPatient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPatientRepository extends MongoRepository<MongoPatient, String> {
}
