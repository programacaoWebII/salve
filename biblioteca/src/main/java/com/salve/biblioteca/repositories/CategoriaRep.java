package com.salve.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salve.biblioteca.entities.Categoria;

public interface CategoriaRep extends JpaRepository<Categoria,Long> {
    
}
