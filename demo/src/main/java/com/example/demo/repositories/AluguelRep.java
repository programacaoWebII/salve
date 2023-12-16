package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Aluguel;


public interface AluguelRep extends JpaRepository<Aluguel,Long>{
    List<Aluguel> findByUsuarioIdName(String usuarioId);
}
