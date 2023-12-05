package com.salve.biblioteca.entities;

import lombok.Data;

@Data
public class RelacaoEntreCategoriaESubCategoria {
    private Categoria categoria;
    private Subcategoria subcategoria;
}
