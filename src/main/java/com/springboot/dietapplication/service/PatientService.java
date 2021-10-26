package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlPatientsUnlikelyCategories;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.repository.CategoryRepository;
import com.springboot.dietapplication.repository.PatientRepository;
import com.springboot.dietapplication.repository.PatientsUnlikelyCategoriesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final CategoryRepository categoryRepository;
    private final PatientsUnlikelyCategoriesRepository patientsUnlikelyCategoriesRepository;

    public PatientService(PatientRepository patientRepository,
                          CategoryRepository categoryRepository,
                          PatientsUnlikelyCategoriesRepository patientsUnlikelyCategoriesRepository) {
        this.patientRepository = patientRepository;
        this.categoryRepository = categoryRepository;
        this.patientsUnlikelyCategoriesRepository = patientsUnlikelyCategoriesRepository;
    }

    public List<PatientType> getAll() {
        List<PatientType> patientTypeList = new ArrayList<>();

        List<PsqlPatient> patients = this.patientRepository.findAll();
        for (PsqlPatient psqlPatient : patients) {
            PatientType patientType = new PatientType(psqlPatient);
            patientType.setUnlikelyCategories(getPatientsUnlikelyCategories(psqlPatient));
            patientTypeList.add(patientType);
        }

        return patientTypeList;
    }

    public PatientType getPatientById(Long patientId) {
        Optional<PsqlPatient> patient = this.patientRepository.findById(patientId);
        return patient.map(PatientType::new).orElseGet(PatientType::new);
    }

    public ResponseEntity<PatientType> insert(PatientType patient) {
        PsqlPatient psqlPatient = new PsqlPatient(patient);

        this.patientRepository.save(psqlPatient);
        patient.setId(String.valueOf(psqlPatient.getId()));
        storePatientsUnlikelyCategories(patient);

        return ResponseEntity.ok().body(patient);
    }

    public ResponseEntity<Void> delete(Long id) {
        this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(id);
        this.patientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private Set<String> getPatientsUnlikelyCategories(PsqlPatient psqlPatient) {
        Set<PsqlPatientsUnlikelyCategories> unlikelyCategories =
                this.patientsUnlikelyCategoriesRepository.findPsqlPatientsUnlikelyCategoriesByPatientId(psqlPatient.getId());

        Set<String> categories = new HashSet<>();
        for (PsqlPatientsUnlikelyCategories category : unlikelyCategories) {
            Optional<PsqlCategory> psqlCategory = this.categoryRepository.findById(category.getCategoryId());
            psqlCategory.ifPresent(value -> categories.add(value.getSubcategory()));
        }

        return categories;
    }

    private void storePatientsUnlikelyCategories(PatientType patientType) {
        this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(Long.parseLong(patientType.getId()));
        if (patientType.getUnlikelyCategories() == null) return;
        for (String category : patientType.getUnlikelyCategories()) {

            PsqlCategory psqlCategory = this.categoryRepository.findPsqlCategoryBySubcategory(category);
            if (psqlCategory == null) return;

            PsqlPatientsUnlikelyCategories patientsUnlikelyCategories =
                    new PsqlPatientsUnlikelyCategories(Long.parseLong(patientType.getId()), psqlCategory.getId());
            this.patientsUnlikelyCategoriesRepository.save(patientsUnlikelyCategories);

        }
    }
}
