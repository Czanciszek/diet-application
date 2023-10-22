package com.springboot.dietapplication.model.psql.user;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class PsqlUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 8229292938443984564L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_type_id")
    private Long userTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "pdf_footer")
    private String pdfFooter;

    public PsqlUser() { }

    public PsqlUser(UserEntity userEntity) {
        this.id = Long.valueOf(userEntity.getId());
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.email = userEntity.getEmail();
        this.imageId = userEntity.getImageId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
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
