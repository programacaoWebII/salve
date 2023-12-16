package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Livro;

public interface LivroRep extends JpaRepository<Livro,Long>{

}
