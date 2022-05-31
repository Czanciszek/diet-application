package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<PatientType> patientList = this.patientService.getAll();
            return ResponseEntity.ok(patientList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable("patientId") Long patientId) {
        try {
            PatientType patient = this.patientService.getPatientById(patientId);
            return ResponseEntity.ok(patient);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patientId);
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody PatientType patient) {
        try {
            PatientType patientType = this.patientService.insert(patient);
            return ResponseEntity.ok().body(patientType);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patient);
        }
    }

    @PutMapping(path = "/{patientId}", produces = "application/json")
    ResponseEntity<?> update(@RequestBody PatientType patient) {
        try {
            PatientType patientType = this.patientService.insert(patient);
            return ResponseEntity.ok().body(patientType);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patient);
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            this.patientService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}
