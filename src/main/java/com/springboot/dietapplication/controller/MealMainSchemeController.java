package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.MealMainScheme;
import com.springboot.dietapplication.model.type.MealMainSchemeCreateRequest;
import com.springboot.dietapplication.service.MealMainSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mainSchemes")
public class MealMainSchemeController {

    @Autowired
    MealMainSchemeService mealMainSchemeService;

    @GetMapping(path = "/{patientId}")
    ResponseEntity<?> getMainSchemesByPatientId(@PathVariable("patientId") String patientId) {
        List<MealMainScheme> mealMainSchemes = this.mealMainSchemeService.getByPatientId(patientId);
        return ResponseEntity.ok(mealMainSchemes);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody MealMainSchemeCreateRequest createRequest) {
        try {
            var mealMainScheme = this.mealMainSchemeService.insert(createRequest);
            return ResponseEntity.ok().body(mealMainScheme);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createRequest);
        }
    }
//
//    @PutMapping(produces = "application/json")
//    ResponseEntity<?> update(@RequestBody Patient patient) {
//        try {
//            Patient patientType = this.patientService.update(patient);
//            return ResponseEntity.ok().body(patientType);
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<HttpStatus>(e.getStatusCode());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(patient);
//        }
//    }
//
//    @DeleteMapping(path = "/{id}")
//    ResponseEntity<?> deletePatient(@PathVariable String id) {
//        try {
//            this.patientService.delete(id);
//            return ResponseEntity.ok().build();
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<HttpStatus>(e.getStatusCode());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(id);
//        }
//    }
}
