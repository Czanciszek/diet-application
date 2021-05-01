package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.patient.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

}
