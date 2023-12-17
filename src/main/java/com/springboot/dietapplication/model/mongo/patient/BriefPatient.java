package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import com.springboot.dietapplication.model.type.SexType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BriefPatient {

    private String id;
    private String name;
    private String surname;
    private SexType sex;

    public BriefPatient() {}

    public BriefPatient(PsqlPatient psqlPatient) {
        this.id = String.valueOf(psqlPatient.getId());
        this.name = psqlPatient.getName();
        this.surname = psqlPatient.getSurname();
        this.sex = psqlPatient.isSex() ? SexType.FEMALE : SexType.MALE;
    }

    public BriefPatient(MongoPatient mongoPatient) {
        this.id = mongoPatient.getId();
        this.name = mongoPatient.getName();
        this.surname = mongoPatient.getSurname();
        this.sex = mongoPatient.getSex();
    }
}
