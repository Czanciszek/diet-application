package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.clinic.MongoClinic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ClinicType implements Serializable {

    @Serial
    private static final long serialVersionUID = 6918603749943343516L;

    private String id;
    private String name;
    private String address;

    public ClinicType(MongoClinic mongoClinic) {
        this.id = mongoClinic.getId();

        this.name = mongoClinic.getName();
        this.address = mongoClinic.getAddress();
    }

}
