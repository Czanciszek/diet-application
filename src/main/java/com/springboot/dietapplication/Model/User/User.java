package com.springboot.dietapplication.Model.User;

import com.springboot.dietapplication.Model.Base.BaseDoc;

public class User extends BaseDoc {

    private String password;
    private String primaryImageId;
    private UserType userType;

    public User(String id, String name, String password, String primaryImageId) {
        this.primaryImageId = primaryImageId;
        this.password = password;

        setId(id);
        setName(name);
        setPrimaryImageId(primaryImageId);
        setType(UserType.ADMIN.toString());
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

    public String getPrimaryImageId() {
        return primaryImageId;
    }

    public void setPrimaryImageId(String primaryImageId) {
        this.primaryImageId = primaryImageId;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
