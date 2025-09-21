package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.MeasurementType;
import com.springboot.dietapplication.model.type.Patient;
import com.springboot.dietapplication.service.PatientServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/patients")
public class PatientControllerV2 {

    @Autowired
    PatientServiceV2 patientService;

    @GetMapping
    ResponseEntity<?> getAll() {
        List<Patient> patientList = this.patientService.getAll();
        return ResponseEntity.ok(patientList);
    }

    @GetMapping(path = "/{patientId}")
    ResponseEntity<?> getPatientById(@PathVariable("patientId") String patientId) {
        Patient patient = this.patientService.getPatientById(patientId);
        return ResponseEntity.ok(patient);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody Patient patient) {
        Patient patientType = this.patientService.insert(patient);
        return ResponseEntity.ok().body(patientType);
    }

    @PostMapping(path = "/measurements", produces = "application/json")
    ResponseEntity<?> insertMeasurement(@RequestBody MeasurementType measurement) {
        MeasurementType measurementType = this.patientService.insertMeasurement(measurement);
        return ResponseEntity.ok().body(measurementType);
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<?> update(@RequestBody Patient patient) {
        Patient patientType = this.patientService.update(patient);
        return ResponseEntity.ok().body(patientType);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deletePatient(@PathVariable String id) {
        this.patientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
