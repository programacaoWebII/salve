package com.example.demo.entities.auxEntity;

import lombok.Data;

@Data
public class Credentials {
    private String type = "password";
    private String value;
    private boolean temporary = false;
    public Credentials(String value){
        this.value = value;
    }
}
