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

    private String maintainerUserId;
    private String name;
    private String address;

    private String creationDate;
    private String updateDate;
    private String deletionDate;

    public MongoClinic(ClinicType clinicType) {
        this.id = clinicType.getId();

        this.maintainerUserId = clinicType.getMaintainerUserId();
        this.name = clinicType.getName();
        this.address = clinicType.getAddress();
    }

    @Override
    public int compareTo(MongoClinic o) {
        return this.id.compareTo(o.id);
    }
}
