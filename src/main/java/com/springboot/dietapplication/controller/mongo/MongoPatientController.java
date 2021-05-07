package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.service.mongo.MongoPatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/patients")
public class MongoPatientController {

    private final MongoPatientService patientService;

    public MongoPatientController(MongoPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientType> getAll() {
        return this.patientService.getAll();
    }

    @GetMapping(path = "/{patientId}")
    public PatientType getPatientById(@PathVariable("patientId") String patientId) {
        return this.patientService.getPatientById(patientId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<PatientType> insertPatient(@RequestBody PatientType patient) {
        this.patientService.insert(patient);
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping(path = "/{patientId}", produces = "application/json")
    ResponseEntity<PatientType> updatePatient(@RequestBody PatientType patient) {
        this.patientService.insert(patient);
        return ResponseEntity.ok().body(patient);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> deletePatient(@PathVariable String id) {
        this.patientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
