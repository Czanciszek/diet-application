package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.MongoMealMainSchemeRepository;
import com.springboot.dietapplication.repository.MongoProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealMainSchemeService {

    @Autowired
    MongoMealMainSchemeRepository mealMainSchemeRepository;

    @Autowired
    MongoProductRepository productRepository;

    public List<MealMainScheme> getByPatientId(String patientId) throws ResponseStatusException {
        return mealMainSchemeRepository
                .findByPatientId(patientId)
                .stream()
                .map(MealMainScheme::new)
                .collect(Collectors.toList());
    }

    public MealMainScheme insert(MealMainSchemeCreateRequest mealMainSchemeCreateRequest) {

        var mainScheme = new MongoMealMainScheme(mealMainSchemeCreateRequest);

        mealMainSchemeRepository.insert(mainScheme);

        return new MealMainScheme(mainScheme);
    }

//    public MealMainScheme insert(MealMainSchemeCreateRequest mealMainSchemeCreateRequest) {
//
//        var productIds = new ArrayList<>(
//                Arrays.asList(
//                    mealMainSchemeCreateRequest.getProteinsProductId(),
//                    mealMainSchemeCreateRequest.getFatsProductId(),
//                    mealMainSchemeCreateRequest.getCarbohydratesProductId(),
//                    mealMainSchemeCreateRequest.getFibresProductId()
//                )
//        );
//
//        Map<String, MongoProduct> productsById = productRepository
//                .findAllById(productIds)
//                .stream()
//                .collect(Collectors.toMap(MongoProduct::getId, p -> p));
//
//    }
//
//    public Patient update(Patient patient) {
//
//        if (patient.getId() == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient id cannot be null");
//
//        // TODO: Validate all required fields
//        Optional<MongoPatient> mongoPatient = patientRepository.findById(patient.getId());
//        if (mongoPatient.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");
//
////        UserEntity user = userDetailsService.getCurrentUser();
////        if (!user.getUserType().equals(UserType.ADMIN.name) && !psqlProduct.get().getUserId().equals(user.getId()))
////            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized attempt for updating product");
//
//        MongoPatient updatedPatient = new MongoPatient(patient);
////        updatedPatient.setUserId(mongoPatient.get().getUserId());
//        updatedPatient.setCreationDate(mongoPatient.get().getCreationDate());
//
//        String currentDate =  DateFormatter.getInstance().getCurrentDate();
//        updatedPatient.setUpdateDate(currentDate);
//
//        patientRepository.save(updatedPatient);
//
//        // Update patient reference in Menu objects
//        menuService.updateMenuPatients(updatedPatient);
//
//        return new Patient(updatedPatient);
//    }
//
//    public void delete(String id) throws ResponseStatusException {
//
//        Optional<MongoPatient> mongoPatient = mongoPatientRepository.findById(id);
//        if (mongoPatient.isEmpty()) return;
//
////        UserEntity user = userDetailsService.getCurrentUser();
////        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId())+)
////            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");
//
//        String currentDate =  DateFormatter.getInstance().getCurrentDate();
//        mongoPatient.get().setDeletionDate(currentDate);
//
//        mongoPatientRepository.save(mongoPatient.get());
//    }
}