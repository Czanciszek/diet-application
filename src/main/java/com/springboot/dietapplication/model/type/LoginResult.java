package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 4159009491099581799L;

    private String token;

    public LoginResult(String token) {
        this.token = token;
    }
}
