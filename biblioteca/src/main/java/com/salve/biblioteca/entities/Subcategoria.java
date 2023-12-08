package com.salve.biblioteca.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Subcategoria {
     
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String nome;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;
}
