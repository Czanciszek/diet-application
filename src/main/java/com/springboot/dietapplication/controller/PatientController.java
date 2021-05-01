package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.patient.Patient;
import com.springboot.dietapplication.repository.mongo.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public List<Patient> getAll() {
        return this.patientRepository.findAll();
    }

    @GetMapping(path = "/{patientId}")
    public Optional<Patient> getFilteredProducts(@PathVariable("patientId") String patientId) {
        return this.patientRepository.findById(patientId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Patient> insertPatient(@RequestBody Patient patient) throws NoSuchFieldException {
        patientRepository.save(patient);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{patientId}", produces = "application/json")
    ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        patientRepository.save(patient);
        return ResponseEntity.ok().body(patient);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Patient> deletePatient(@PathVariable String id) {
        patientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
