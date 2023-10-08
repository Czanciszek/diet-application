package com.springboot.dietapplication.model.psql.user;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3196537900995603L;

    @Id
    private Long id;
    private String userType;
    private String name;
    private String password;
    private String email;
    private String imageId;
    private String pdfFooter;

    public UserEntity() {

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

    public String getPdfFooter() {
        return pdfFooter;
    }

    public void setPdfFooter(String pdfFooter) {
        this.pdfFooter = pdfFooter;
    }
}
