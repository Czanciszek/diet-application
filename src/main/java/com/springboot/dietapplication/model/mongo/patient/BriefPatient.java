package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.SexType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BriefPatient {

    private String id;
    private String name;
    private String surname;
    private SexType sex;

    public BriefPatient(MongoPatient mongoPatient) {
        this.id = mongoPatient.getId();
        this.name = mongoPatient.getName();
        this.surname = mongoPatient.getSurname();
        this.sex = mongoPatient.getSex();
    }
}
