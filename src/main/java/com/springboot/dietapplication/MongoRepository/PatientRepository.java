package com.springboot.dietapplication.MongoRepository;

import com.springboot.dietapplication.Model.Patient.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

}
