package com.springboot.dietapplication.model.type;

import java.io.Serial;
import java.io.Serializable;

public class LoginResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 4159009491099581799L;

    private String token;

    public LoginResult() {
    }

    public LoginResult(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
