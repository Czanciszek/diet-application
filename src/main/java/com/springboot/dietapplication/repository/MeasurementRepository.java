package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.patient.Measurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, String> {

    List<Measurement> findByPatientDocRefId(String id);
}
