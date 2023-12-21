package com.salve.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salve.biblioteca.entities.Subcategoria;

public interface SubcategoriaRep extends JpaRepository<Subcategoria,Long> {
}
