package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.clinic.MongoClinic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoClinicRepository extends MongoRepository<MongoClinic, String> {
}
