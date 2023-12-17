package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Grupo;




public interface GrupoRep extends JpaRepository<Grupo,Long>{
    Grupo findById(long id);
    @Query("SELECT g FROM Grupo g JOIN g.subcategorias s " +
        "WHERE s.id IN :subcategorias " +
        "GROUP BY g.id " +
        "HAVING COUNT(DISTINCT s.id) = :count")
    List<Grupo> findAllBySubcategorias(@Param("subcategorias") List<Long> subcategorias, @Param("count") Long count);
    Grupo findByLivrosId(long id);
}
