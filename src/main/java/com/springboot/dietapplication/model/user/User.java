package com.springboot.dietapplication.model.user;

import com.springboot.dietapplication.model.base.BaseDoc;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User extends BaseDoc {

    private String password;
    private UserType userType;

    public User(String name, String password, String primaryImageId) {
        setName(name);
        setPrimaryImageId(primaryImageId);

        this.password = password;
        this.userType = UserType.ADMIN;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
