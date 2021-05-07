package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.repository.psql.PsqlPatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PsqlPatientService {

    private final PsqlPatientRepository patientRepository;

    public PsqlPatientService(PsqlPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientType> getAll() {
        List<PatientType> patientTypeList = new ArrayList<>();

        List<PsqlPatient> patients = this.patientRepository.findAll();
        for (PsqlPatient psqlPatient : patients) {
            PatientType patientType = new PatientType(psqlPatient);
            patientTypeList.add(patientType);
        }

        return patientTypeList;
    }

    public PatientType getPatientById(Long patientId) {
        Optional<PsqlPatient> patient = this.patientRepository.findById(patientId);
        return patient.map(PatientType::new).orElseGet(PatientType::new);
    }

    public ResponseEntity<PatientType> insert(PatientType patient) {
        PsqlPatient psqlDish = new PsqlPatient(patient);

        this.patientRepository.save(psqlDish);
        patient.setId(String.valueOf(psqlDish.getId()));

        return ResponseEntity.ok().body(patient);
    }

    public ResponseEntity<Void> delete(Long id) {
        this.patientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
