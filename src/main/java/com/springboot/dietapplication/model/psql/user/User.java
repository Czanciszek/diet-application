package com.springboot.dietapplication.model.psql.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@SecondaryTable(
        name = "user_types",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"),
        foreignKey = @ForeignKey(name = "user_type_id")
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", table = "user_types")
    private String userType;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "image_id")
    private String imageId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
