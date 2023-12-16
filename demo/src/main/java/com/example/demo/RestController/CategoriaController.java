package com.example.demo.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Categoria;
import com.example.demo.repositories.CategoriaRep;

@RestController
public class CategoriaController {
    @Autowired
    private CategoriaRep crp;
    @GetMapping("/categorias")
    public List<Categoria> getCategorias() {
        List<Categoria> cat =crp.findAll();
        return cat;
    }
    @GetMapping("/categoriasExiste")
    public boolean existeEstaCat(@RequestParam String nome){
        return crp.existsByNome(nome);
    }
    
    @GetMapping("/categoriasPosting")
    public HttpStatus postCategoria(@RequestParam String nome, @RequestParam String descricao) {
        try{
            Categoria salva = new Categoria();
            salva.setNome(nome);
            salva.setDescricao(descricao);
            salva = crp.save(salva);
            if(salva.getId()!=0){
                return HttpStatus.CREATED;
            }else{
                return HttpStatus.NO_CONTENT;
            }
        }catch(Exception e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }
}
