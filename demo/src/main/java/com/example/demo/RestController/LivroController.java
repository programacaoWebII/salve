package com.example.demo.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Livro;
import com.example.demo.repositories.LivroRep;


@RestController
public class LivroController {
    @Autowired
    LivroRep lRep;
    @GetMapping("/livros/todos")
    public java.util.List<Livro> getTodosLivros() {
        return lRep.findAll();
    }
    
}
