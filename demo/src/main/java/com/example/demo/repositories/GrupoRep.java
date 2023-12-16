package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Grupo;


public interface GrupoRep extends JpaRepository<Grupo,Long>{
    Grupo findById(long id);
}
