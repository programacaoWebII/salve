package com.salve.biblioteca.entities.auxEntity;

import lombok.Data;

@Data
public class User {
    private String password;
    private String clientId = "SALVE";
    private String grantType = "password";
    private String username;
}
