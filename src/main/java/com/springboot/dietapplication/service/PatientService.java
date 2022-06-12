package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlPatientsUnlikelyCategories;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.model.type.UserType;
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
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for getting patient");

        return new PatientType(patient.get());
    }

    public PatientType getPatientByMenuId(Long menuId) throws ResponseStatusException {
        MenuType menu = this.menuService.getMenuById(menuId);
        Optional<PsqlPatient> patient = this.patientRepository.findById(menu.getPatientId());

        if (!patient.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for getting patient");

        return new PatientType(patient.get());
    }

    public PatientType insert(PatientType patientType) {

        // TODO: Validate all required fields
        PsqlPatient psqlPatient = new PsqlPatient(patientType);

        UserEntity user = userDetailsService.getCurrentUser();
        psqlPatient.setUserId(user.getId());

        this.patientRepository.save(psqlPatient);
        patientType.setId(psqlPatient.getId());

        storePatientsUnlikelyCategories(patientType);

        return patientType;
    }

    public PatientType update(PatientType patientType) {

        // TODO: Validate all required fields
        if (patientType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient id cannot be null");

        Optional<PsqlPatient> patient = this.patientRepository.findById(patientType.getId());
        if (!patient.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient does not exist");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating patient");

        PsqlPatient psqlPatient = new PsqlPatient(patientType);
        psqlPatient.setUserId(patient.get().getUserId());

        this.patientRepository.save(psqlPatient);

        storePatientsUnlikelyCategories(patientType);

        return patientType;
    }

    public void delete(Long id) throws ResponseStatusException {

        Optional<PsqlPatient> patient = this.patientRepository.findById(id);
        if (!patient.isPresent())
            return;

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for deleting patient");

        this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(id);
        this.patientRepository.deleteById(id);

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
