package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.clinic.MongoClinic;
import com.springboot.dietapplication.model.type.Clinic;
import com.springboot.dietapplication.repository.MongoClinicRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    @Autowired
    MongoClinicRepository clinicRepository;

    public List<Clinic> getAll() {

        List<MongoClinic> clinicList = clinicRepository.findAll();

        return clinicList
                .stream()
                .filter(c -> StringUtils.isEmpty(c.getDeletionDate()))
                .map(Clinic::new)
                .collect(Collectors.toList());
    }

    public Clinic getClinicById(String clinicId) throws ResponseStatusException {
        MongoClinic clinic = clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));

        return new Clinic(clinic);
    }

    public Clinic insert(Clinic clinic) {

        MongoClinic mongoClinic = new MongoClinic(clinic);

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoClinic.setCreationDate(currentDate);
        mongoClinic.setUpdateDate(currentDate);

        clinicRepository.save(mongoClinic);

        return new Clinic(mongoClinic);
    }

    public Clinic update(Clinic clinic) {

        if (clinic.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clinic id cannot be null");

        Optional<MongoClinic> mongoClinic = clinicRepository.findById(clinic.getId());
        if (mongoClinic.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic does not exist");

        MongoClinic updatedClinic = new MongoClinic(clinic);
        updatedClinic.setCreationDate(mongoClinic.get().getCreationDate());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        updatedClinic.setUpdateDate(currentDate);

        clinicRepository.save(updatedClinic);

        return new Clinic(updatedClinic);
    }

    public void delete(String id) throws ResponseStatusException {

        Optional<MongoClinic> mongoClinic = clinicRepository.findById(id);
        if (mongoClinic.isEmpty()) return;

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoClinic.get().setDeletionDate(currentDate);

        clinicRepository.save(mongoClinic.get());
    }
}
