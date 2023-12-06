package com.salve.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salve.biblioteca.entities.Grupo;

public interface GrupoRep extends JpaRepository<Grupo,Long>{
    
}
