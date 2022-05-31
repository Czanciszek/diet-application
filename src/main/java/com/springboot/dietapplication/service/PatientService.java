package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlPatientsUnlikelyCategories;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.repository.CategoryRepository;
import com.springboot.dietapplication.repository.PatientRepository;
import com.springboot.dietapplication.repository.PatientsUnlikelyCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    MenuService menuService;
    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PatientsUnlikelyCategoriesRepository patientsUnlikelyCategoriesRepository;

    public List<PatientType> getAll() {
        List<PatientType> patientTypeList = new ArrayList<>();

        UserEntity user = userDetailsService.getCurrentUser();
        List<PsqlPatient> patients = this.patientRepository
                .findAll()
                .stream().filter( patient -> patient.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        for (PsqlPatient psqlPatient : patients) {
            PatientType patientType = new PatientType(psqlPatient);
            patientType.setUnlikelyCategories(getPatientsUnlikelyCategories(psqlPatient));
            patientTypeList.add(patientType);
        }

        return patientTypeList;
    }

    public PatientType getPatientById(Long patientId) throws ResponseStatusException {
        Optional<PsqlPatient> patient = this.patientRepository.findById(patientId);

        if (!patient.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");

        UserEntity user = userDetailsService.getCurrentUser();
        if (patient.get().getUserId().equals(user.getId())) {
            return new PatientType(patient.get());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Getting patient is not authorized");
        }
    }

    public PatientType getPatientByMenuId(Long menuId) throws ResponseStatusException {
        MenuType menu = this.menuService.getMenuById(menuId);
        Optional<PsqlPatient> patient = this.patientRepository.findById(menu.getPatientId());

        if (!patient.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");

        UserEntity user = userDetailsService.getCurrentUser();
        if (patient.get().getUserId().equals(user.getId())) {
            return new PatientType(patient.get());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Getting patient is not authorized");
        }
    }

    public PatientType insert(PatientType patient) throws ResponseStatusException {
        UserEntity user = userDetailsService.getCurrentUser();

        if (patient.getId() != null) {
            // Check authorization for existing patient
            Optional<PsqlPatient> psqlPatient = this.patientRepository.findById(patient.getId());
            if (!psqlPatient.isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Patient doesn't exist anymore");
            if (!psqlPatient.get().getUserId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Updating patient is not authorized");
        }

        PsqlPatient psqlPatient = new PsqlPatient(patient);
        psqlPatient.setUserId(user.getId());

        this.patientRepository.save(psqlPatient);
        patient.setId(psqlPatient.getId());

        storePatientsUnlikelyCategories(patient);

        return patient;
    }

    public void delete(Long id) throws ResponseStatusException {

        Optional<PsqlPatient> patient = this.patientRepository.findById(id);
        if (!patient.isPresent())
            return;

        UserEntity user = userDetailsService.getCurrentUser();
        if (patient.get().getUserId().equals(user.getId())) {
            this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(id);
            this.patientRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Deleting patient is not authorized");
        }

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
        this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(patientType.getId());
        if (patientType.getUnlikelyCategories() == null) return;
        for (String category : patientType.getUnlikelyCategories()) {

            PsqlCategory psqlCategory = this.categoryRepository.findPsqlCategoryBySubcategory(category);
            if (psqlCategory == null) return;

            PsqlPatientsUnlikelyCategories patientsUnlikelyCategories =
                    new PsqlPatientsUnlikelyCategories(patientType.getId(), psqlCategory.getId());
            this.patientsUnlikelyCategoriesRepository.save(patientsUnlikelyCategories);

        }
    }
}
