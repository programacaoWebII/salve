package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Categoria;



public interface CategoriaRep extends JpaRepository<Categoria,Long>{
    Categoria findById(long id);
    java.util.List<Categoria> findAll();
    boolean existsByNome(String nome);
}
