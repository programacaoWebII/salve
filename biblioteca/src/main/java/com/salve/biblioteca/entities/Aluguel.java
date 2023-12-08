package com.salve.biblioteca.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
@Entity
public class Aluguel {
     
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(length = 36)
    
    private String usuario_id;
    @ManyToOne
    private Livro livro;
    private LocalDateTime dataAluguel;
    private LocalDate diaDevolucao;
    private String status;
}
