package com.salve.biblioteca.entities;



import java.util.List;

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
    @OneToMany(mappedBy = "grupo")
    private List<Livro> livros;
    @ManyToMany
    private List<Categoria> categorias;
    @ManyToMany
    private List<Subcategoria> subcategorias;
}
