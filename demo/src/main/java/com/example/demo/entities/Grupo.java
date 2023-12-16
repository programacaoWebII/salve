package com.example.demo.entities;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Grupo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String nome;
    private String imagemLink;
    private int quantidadeDisponivel;
    private String descricao;
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Livro> livros;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.ALL})
    @JsonManagedReference
    private List<Subcategoria> subcategorias;
}
