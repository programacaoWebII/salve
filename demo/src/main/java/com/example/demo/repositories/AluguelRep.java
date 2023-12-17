package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Aluguel;
import com.example.demo.entities.Grupo;


public interface AluguelRep extends JpaRepository<Aluguel,Long>{
    List<Aluguel> findByUsuarioIdName(String usuarioId);
    Aluguel findById(long id);
    List<Aluguel> findByUsuarioIdCpf(String cpf);
    List<Aluguel> findByLivroId(long id);
    List<Aluguel> findAllByLivroGrupoIn(List<Grupo> grupos);
    List<Aluguel> findByUsuarioIdCpfInAndLivroGrupoIn(List<String> cpf,List<Grupo> grupos);
    

}
