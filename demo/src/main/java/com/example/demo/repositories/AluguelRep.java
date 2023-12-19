package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Aluguel;
import com.example.demo.entities.Grupo;


public interface AluguelRep extends JpaRepository<Aluguel,Long>{
    List<Aluguel> findByUsuarioIdName(String usuarioId);
    Aluguel findById(long id);
    List<Aluguel> findByUsuarioIdCpf(String cpf);
    List<Aluguel> findByLivroId(long id);
    @Query("SELECT al FROM Aluguel al WHERE al.livro.grupo IN(:gps)")
    List<Aluguel> findAlugueisComGrupos(@Param("gps") List<Grupo> grupos);
    @Query("SELECT al FROM Aluguel al WHERE al.usuarioId.cpf IN (:cpf) AND al.livro.grupo IN (:grupos)")
    List<Aluguel> findComCPFAndGrupos(@Param("cpf") List<String> cpf, @Param("grupos") List<Grupo> grupos);

    

}
