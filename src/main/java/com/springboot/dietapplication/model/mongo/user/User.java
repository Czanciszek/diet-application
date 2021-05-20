package com.springboot.dietapplication.model.mongo.user;

import com.springboot.dietapplication.model.type.UserType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

    private String id;
    private String name;
    private String primaryImageId;
    private String password;
    private UserType userType;

    public User(String name, String password, String primaryImageId) {
        this.name = name;
        this.primaryImageId = primaryImageId;
        this.password = password;
        this.userType = UserType.ADMIN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrimaryImageId() {
        return primaryImageId;
    }

    public void setPrimaryImageId(String primaryImageId) {
        this.primaryImageId = primaryImageId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
