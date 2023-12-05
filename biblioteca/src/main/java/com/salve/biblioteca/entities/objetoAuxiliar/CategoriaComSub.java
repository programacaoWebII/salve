package com.salve.biblioteca.entities.objetoAuxiliar;

import java.util.List;

import com.salve.biblioteca.entities.Subcategoria;

import lombok.Data;

@Data
public class CategoriaComSub {
    private long id;
    private String nome;
    private String descricao;
    private List<Subcategoria>subcategorias;
}
