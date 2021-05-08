package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.patient.MongoMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoMeasurementRepository extends MongoRepository<MongoMeasurement, String> {

    List<MongoMeasurement> findByPatientId(String id);
}
