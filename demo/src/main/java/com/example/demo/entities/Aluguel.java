package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
@Entity
public class Aluguel {
     
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    @ManyToOne
    @JoinColumn(name = "normalUser_name")
    private NormalUser usuarioId;
    @ManyToOne
    private Livro livro;
    private LocalDateTime dataAluguel;
    private LocalDate diaDevolucao;
    private String status;
}
