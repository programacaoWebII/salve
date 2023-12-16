package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class NormalUser {
    @Id
    @Column(unique = true,length = 36)
    public String name;
    public String givenName;
    public String familyName;
    public String imagemLink;
    public String email;
    @Column(length = 11,unique = true)
    public String cpf;
    public String fullname(){
        return this.givenName+" "+this.familyName;
    }
}
