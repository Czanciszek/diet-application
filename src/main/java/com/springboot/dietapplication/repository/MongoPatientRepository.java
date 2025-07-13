package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.mongo.patient.MongoPatient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPatientRepository extends MongoRepository<MongoPatient, String> {
}
