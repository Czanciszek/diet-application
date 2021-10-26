package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/psql/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientType> getAll() {
        return this.patientService.getAll();
    }

    @GetMapping(path = "/{patientId}")
    public PatientType getPatientById(@PathVariable("patientId") Long patientId) {
        return this.patientService.getPatientById(patientId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<PatientType> insert(@RequestBody PatientType patient) {
        this.patientService.insert(patient);
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping(path = "/{patientId}", produces = "application/json")
    ResponseEntity<PatientType> update(@RequestBody PatientType patient) {
        this.patientService.insert(patient);
        return ResponseEntity.ok().body(patient);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<PatientType> deletePatient(@PathVariable Long id) {
        this.patientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
