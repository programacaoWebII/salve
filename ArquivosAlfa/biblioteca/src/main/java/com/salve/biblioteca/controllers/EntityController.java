package com.salve.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salve.biblioteca.entities.Categoria;
import com.salve.biblioteca.entities.Subcategoria;
import com.salve.biblioteca.repositories.CategoriaRep;



@Controller
@RequestMapping("/fill")
public class EntityController {
    @Autowired
    private CategoriaRep cat;
    @PostMapping("/categoria")
    public long salvaCategoria(@RequestBody Categoria entity) {
        
        cat.save(entity);
        return entity.getId();
    }
    
    @PostMapping("/subcategoria")
    public Long postMethodName(@RequestBody Subcategoria entity) {
        
        return entity.getId();
    }
    
    public String salvaSubcategoria(@RequestParam String param) {
        //TODO: alguma coisa
        return param;
    }

}
