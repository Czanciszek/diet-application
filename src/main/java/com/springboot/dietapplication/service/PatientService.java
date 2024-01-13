package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlAllergenType;
import com.springboot.dietapplication.model.psql.menu.PsqlPatientsUnlikelyCategories;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.psql.patient.PsqlPatientsAllergenTypes;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.mongo.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Deprecated(since = "0.1.0", forRemoval = true)
@Service
public class PatientService {

    @Autowired
    MenuService menuService;
    @Autowired
    MeasurementService measurementService;
    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PatientsUnlikelyCategoriesRepository patientsUnlikelyCategoriesRepository;
    @Autowired
    PatientsAllergenTypesRepository patientsAllergenTypesRepository;
    @Autowired
    AllergenTypeRepository allergenTypeRepository;

    public List<PatientType> getAll() {
        List<PatientType> patientTypeList = new ArrayList<>();

        List<PsqlPatient> patients = patientRepository.findAll();
//        UserEntity user = userDetailsService.getCurrentUser();
//        List<PsqlPatient> patients = this.patientRepository.findAllByUserId(user.getId());

        for (PsqlPatient psqlPatient : patients) {
            PatientType patientType = new PatientType(psqlPatient);
            patientType.setUnlikelyCategories(getPatientsUnlikelyCategories(psqlPatient.getId()));
            patientType.setAllergens(convertAllergenTypes(psqlPatient.getId()));
            patientType.setMeasurements(measurementService.getMeasurementsByPatientId(psqlPatient.getId()));

            List<MenuType> menuTypes = menuService.getMenusByPatientId(psqlPatient.getId());
            patientType.setMenus(menuTypes.stream().map(MenuType::getId).collect(Collectors.toList()));

            patientTypeList.add(patientType);
        }

        return patientTypeList;
    }

    public PatientType getPatientById(Long patientId) throws ResponseStatusException {
        Optional<PsqlPatient> patient = this.patientRepository.findById(patientId);

        if (patient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for getting patient");

        return new PatientType(patient.get());
    }

    public PatientType getPatientByMenuId(Long menuId) throws ResponseStatusException {
        MenuType menu = this.menuService.getMenuById(menuId);
        Optional<PsqlPatient> patient = this.patientRepository.findById(Long.parseLong(menu.getPatient().getId()));

        if (patient.isEmpty())
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
        psqlPatient.setUserId(Long.valueOf(user.getId()));

        this.patientRepository.save(psqlPatient);
        patientType.setId(String.valueOf(psqlPatient.getId()));

        storePatientsUnlikelyCategories(patientType);
        storePatientsAllergens(patientType);

        return patientType;
    }

    public PatientType update(PatientType patientType) {

        // TODO: Validate all required fields
        if (patientType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient id cannot be null");

        Optional<PsqlPatient> patient = this.patientRepository.findById(Long.valueOf(patientType.getId()));
        if (patient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient does not exist");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating patient");

        PsqlPatient psqlPatient = new PsqlPatient(patientType);
        psqlPatient.setUserId(patient.get().getUserId());

        this.patientRepository.save(psqlPatient);

        storePatientsUnlikelyCategories(patientType);
        storePatientsAllergens(patientType);

        return patientType;
    }

    public void delete(Long id) throws ResponseStatusException {

        Optional<PsqlPatient> patient = this.patientRepository.findById(id);
        if (patient.isEmpty())
            return;

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for deleting patient");

        this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(id);
        this.patientRepository.deleteById(id);

    }

    private Set<String> getPatientsUnlikelyCategories(long patientId) {
        Set<PsqlPatientsUnlikelyCategories> unlikelyCategories =
                this.patientsUnlikelyCategoriesRepository.findPsqlPatientsUnlikelyCategoriesByPatientId(patientId);

        Set<String> categories = new HashSet<>();
        for (PsqlPatientsUnlikelyCategories category : unlikelyCategories) {
            Optional<PsqlCategory> psqlCategory = this.categoryRepository.findById(category.getCategoryId());
            psqlCategory.ifPresent(value -> categories.add(value.getSubcategory()));
        }

        return categories;
    }

    private Set<AllergenType> convertAllergenTypes(long patientId) {
        Set<AllergenType> allergenTypes = new HashSet<>();

        Set<PsqlPatientsAllergenTypes> patientsAllergens = patientsAllergenTypesRepository.findPsqlPatientsAllergenTypesByPatientId(patientId);

        patientsAllergens.forEach( patientAllergenType -> {
            Optional<AllergenType> optionalAllergenType = AllergenType.valueOf(patientAllergenType.getAllergenTypeId());
            optionalAllergenType.ifPresent(allergenTypes::add);
        });

        return allergenTypes;
    }

    private void storePatientsUnlikelyCategories(PatientType patientType) {
        long patientId = Long.parseLong(patientType.getId());
        this.patientsUnlikelyCategoriesRepository.deleteAllByPatientId(patientId);
        if (patientType.getUnlikelyCategories() == null) return;
        for (String category : patientType.getUnlikelyCategories()) {

            PsqlCategory psqlCategory = this.categoryRepository.findPsqlCategoryBySubcategory(category);
            if (psqlCategory == null) return;

            PsqlPatientsUnlikelyCategories patientsUnlikelyCategories =
                    new PsqlPatientsUnlikelyCategories(patientId, psqlCategory.getId());
            this.patientsUnlikelyCategoriesRepository.save(patientsUnlikelyCategories);

        }
    }

    private void storePatientsAllergens(PatientType patientType) {
        long patientId = Long.parseLong(patientType.getId());
        this.patientsAllergenTypesRepository.deleteAllByPatientId(patientId);

        if (patientType.getAllergens() == null) return;
        Set<PsqlPatientsAllergenTypes> patientAllergenTypes = new HashSet<>();

        for (AllergenType allergenType : patientType.getAllergens()) {

            PsqlAllergenType psqlAllergenType = this.allergenTypeRepository.getPsqlAllergenTypeByName(allergenType.name());
            if (psqlAllergenType == null) continue;

            PsqlPatientsAllergenTypes patientAllergenType = new PsqlPatientsAllergenTypes(patientId, psqlAllergenType.getId());
            patientAllergenTypes.add(patientAllergenType);
        }

        this.patientsAllergenTypesRepository.saveAll(patientAllergenTypes);
    }
}
