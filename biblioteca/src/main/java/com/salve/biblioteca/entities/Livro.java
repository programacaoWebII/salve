package com.salve.biblioteca.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Livro {
     
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String emprestavel;
    private String status;
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAquisicao;
    
    
}
