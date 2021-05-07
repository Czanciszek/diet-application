package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.patient.MongoPatient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPatientRepository extends MongoRepository<MongoPatient, String> {

}
