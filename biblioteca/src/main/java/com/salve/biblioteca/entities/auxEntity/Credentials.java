package com.salve.biblioteca.entities.auxEntity;

import lombok.Data;

@Data
public class Credentials {
    private String type = "password";
    private String value;
    private boolean temporary = false;
}
