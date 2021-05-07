package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.patient.MongoPatient;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.repository.mongo.MongoPatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoPatientService {

    private final MongoPatientRepository patientRepository;

    public MongoPatientService(MongoPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientType> getAll() {
        List<PatientType> patientTypeList = new ArrayList<>();

        List<MongoPatient> patients = this.patientRepository.findAll();
        for (MongoPatient psqlPatient : patients) {
            PatientType patientType = new PatientType(psqlPatient);
            patientTypeList.add(patientType);
        }

        return patientTypeList;
    }

    public PatientType getPatientById(String patientId) {
        Optional<MongoPatient> patient = this.patientRepository.findById(patientId);
        return patient.map(PatientType::new).orElseGet(PatientType::new);
    }

    public ResponseEntity<PatientType> insert(PatientType patient) {
        MongoPatient mongoPatient = new MongoPatient(patient);

        this.patientRepository.save(mongoPatient);
        patient.setId(String.valueOf(mongoPatient.getId()));

        return ResponseEntity.ok().body(patient);
    }

    public ResponseEntity<Void> delete(String id) {
        this.patientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
