package com.salve.biblioteca.entities.auxEntity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserCreated {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled = true;
    private List<Credentials> credentials;
   
    public void setPassword(String password){
        ArrayList<Credentials> temp = new ArrayList<Credentials>();
        Credentials cred = new Credentials(password);
        temp.add(cred);
        this.credentials = temp.subList(0, temp.size());
    }
    
}
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
