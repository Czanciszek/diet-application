package com.springboot.dietapplication.model.mongo.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3196537900995603L;

    @Id
    private String id;
    private String userType;
    private String name;
    private String password;
    private String email;
    private String imageId;
    private String pdfFooter;

    public UserEntity() { }

    public UserEntity(MongoUserEntity mongoUserEntity) {
        this.id = mongoUserEntity.getId();
        this.userType = mongoUserEntity.getUserType();
        this.name = mongoUserEntity.getName();
        this.password = mongoUserEntity.getPassword();
        this.email = mongoUserEntity.getEmail();
        this.imageId = mongoUserEntity.getImageId();
        this.pdfFooter = mongoUserEntity.getPdfFooter();
    }

}
