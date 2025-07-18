package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.MeasurementType;
import com.springboot.dietapplication.model.type.Patient;
import com.springboot.dietapplication.service.PatientServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v2/patients")
public class PatientControllerV2 {

    @Autowired
    PatientServiceV2 patientService;

    @GetMapping
    ResponseEntity<?> getAll() {
        try {
            List<Patient> patientList = this.patientService.getAll();
            return ResponseEntity.ok(patientList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{patientId}")
    ResponseEntity<?> getPatientById(@PathVariable("patientId") String patientId) {
        try {
            Patient patient = this.patientService.getPatientById(patientId);
            return ResponseEntity.ok(patient);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patientId);
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody Patient patient) {
        try {
            Patient patientType = this.patientService.insert(patient);
            return ResponseEntity.ok().body(patientType);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patient);
        }
    }

    @PostMapping(path = "/measurements", produces = "application/json")
    ResponseEntity<?> insertMeasurement(@RequestBody MeasurementType measurement) {
        try {
            MeasurementType measurementType = this.patientService.insertMeasurement(measurement);
            return ResponseEntity.ok().body(measurementType);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(measurement);
        }
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<?> update(@RequestBody Patient patient) {
        try {
            Patient patientType = this.patientService.update(patient);
            return ResponseEntity.ok().body(patientType);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patient);
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deletePatient(@PathVariable String id) {
        try {
            this.patientService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}
