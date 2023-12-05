package com.salve.biblioteca.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Subcategoria {
     
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public long id;
    public String nome;
    public String descricao;
}
