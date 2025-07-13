package com.springboot.dietapplication.model.mongo.clinic;

import com.springboot.dietapplication.model.type.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("clinics")
public class MongoClinic implements Comparable<MongoClinic> {

    @Id
    private String id;

    private String name;
    private String address;

    private String creationDate;
    private String updateDate;
    private String deletionDate;

    public MongoClinic(Clinic clinic) {
        this.id = clinic.getId();

        this.name = clinic.getName();
        this.address = clinic.getAddress();
    }

    @Override
    public int compareTo(MongoClinic o) {
        return this.id.compareTo(o.id);
    }
}
