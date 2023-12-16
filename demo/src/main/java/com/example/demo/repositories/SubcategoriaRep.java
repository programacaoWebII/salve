package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Subcategoria;


public interface SubcategoriaRep extends JpaRepository<Subcategoria,Long>{
    List<Subcategoria> findByCategoria(Categoria categoria);
    @Query("SELECT ob, ob.categoria.id FROM Subcategoria ob JOIN FETCH ob.categoria")
    List<Subcategoria> findTudo();
    boolean existsByNome(String nome);
    boolean existsByNomeAndCategoriaId(String nome, long categoriaId);
    Subcategoria findById(long id);
}
