package com.springboot.dietapplication.model.mongo.user;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Document("users")
public class MongoUserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3126336981636118876L;

    @Id
    private String id;
    private String userType;
    private String name;
    private String password;
    private String email;
    private String imageId;
    private String pdfFooter;

    public MongoUserEntity(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.userType = userEntity.getUserType();
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.email = userEntity.getEmail();
        this.imageId = userEntity.getImageId();
        this.pdfFooter = userEntity.getPdfFooter();
    }

}
