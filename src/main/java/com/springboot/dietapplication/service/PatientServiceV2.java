package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.patient.MongoPatient;
import com.springboot.dietapplication.model.mongo.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.mongo.MongoPatientRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientServiceV2 {

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    MenuServiceV2 menuService;

    @Autowired
    MongoPatientRepository mongoPatientRepository;

    public List<PatientType> getAll() {

        UserEntity user = userDetailsService.getCurrentUser();
        List<MongoPatient> mongoProductList = mongoPatientRepository.findAll();

        // TODO: Consider filtering userID in Mongo

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()) &&
                        p.getUserId().equals(String.valueOf(user.getId())))
                .map(PatientType::new)
                .collect(Collectors.toList());
    }

    public PatientType getPatientById(String patientId) throws ResponseStatusException {
        Optional<MongoPatient> patient = mongoPatientRepository.findById(patientId);

        if (patient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");

        UserEntity user = userDetailsService.getCurrentUser();
        if (!user.getUserType().equals(UserType.ADMIN.name) && !patient.get().getUserId().equals(String.valueOf(user.getId())))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for patient get");

        return new PatientType(patient.get());
    }

    public PatientType getPatientByMenuId(Long menuId) throws ResponseStatusException {
        // TODO: Put such patient data in MongoMenu
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "In Progress");
    }

    public PatientType insert(PatientType patientType) {

        // TODO: Validate all required fields
        MongoPatient mongoPatient = new MongoPatient(patientType);

        UserEntity user = userDetailsService.getCurrentUser();
        mongoPatient.setUserId(user.getId());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoPatient.setCreationDate(currentDate);
        mongoPatient.setUpdateDate(currentDate);

        mongoPatientRepository.save(mongoPatient);

        return new PatientType(mongoPatient);
    }

    public PatientType update(PatientType patientType) {

        if (patientType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient id cannot be null");

        // TODO: Validate all required fields
        Optional<MongoPatient> mongoPatient = mongoPatientRepository.findById(patientType.getId());
        if (mongoPatient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !psqlProduct.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating product");

        MongoPatient updatedPatient = new MongoPatient(patientType);
        updatedPatient.setUserId(mongoPatient.get().getUserId());
        updatedPatient.setCreationDate(mongoPatient.get().getCreationDate());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        updatedPatient.setUpdateDate(currentDate);

        mongoPatientRepository.save(updatedPatient);

        // Update patient reference in Menu objects
        menuService.updateMenuPatients(updatedPatient);

        return new PatientType(updatedPatient);
    }

    public MeasurementType insertMeasurement(MeasurementType measurementType) {
        if (measurementType.getPatientId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient id cannot be null");

        Optional<MongoPatient> mongoPatient = mongoPatientRepository.findById(measurementType.getPatientId());
        if (mongoPatient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");

        List<MeasurementType> measurements = mongoPatient.get().getMeasurements();
        measurements.add(measurementType);

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoPatient.get().setUpdateDate(currentDate);

        mongoPatientRepository.save(mongoPatient.get());

        return measurementType;
    }

    public void delete(String id) throws ResponseStatusException {

        Optional<MongoPatient> mongoPatient = mongoPatientRepository.findById(id);
        if (mongoPatient.isEmpty()) return;

//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoPatient.get().setDeletionDate(currentDate);

        mongoPatientRepository.save(mongoPatient.get());
    }
}
