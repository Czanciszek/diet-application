package com.springboot.dietapplication.model.psql.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserEntity implements Serializable {

    @Id
    private Long id;
    private String userType;
    private String name;
    private String password;
    private String email;
    private String imageId;

    public UserEntity() {

    }
    
    public UserEntity(PsqlUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.imageId = user.getImageId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
