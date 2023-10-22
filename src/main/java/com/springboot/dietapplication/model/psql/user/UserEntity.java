package com.springboot.dietapplication.model.psql.user;

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

}
