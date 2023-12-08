package com.salve.biblioteca.entities.auxEntity;

import lombok.Data;

@Data
public class UserCreated {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled = true;
    private Credentials credentials;
    /*
    {
        "username": "seu_usuario",
        "email": "seu_email@email.com",
        "firstName": "SeuNome",
        "lastName": "SeuSobrenome",
        "enabled": true,
        "credentials": [
            {
                "type": "password",
                "value": "sua_senha",
                "temporary": false
            }
        ]
    }
     */
}
