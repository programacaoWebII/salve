package com.example.demo.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Livro {
     
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private boolean emprestavel;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "grupo_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "nome")
    @JsonIdentityReference(alwaysAsId = true)
    private Grupo grupo;
    private LocalDate dataAquisicao;
    @PrePersist
    public void prePersist() {
        if (dataAquisicao == null) {
            dataAquisicao = LocalDate.now();
        }
    }
    public Livro(boolean emprestavel,String status,Grupo grupo){
        this.emprestavel = emprestavel;
        this.status = status;
        this.grupo = grupo;
    }
    public Livro(){}
}
