package com.example.demo.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Subcategoria;
import com.example.demo.repositories.CategoriaRep;
import com.example.demo.repositories.SubcategoriaRep;

@RestController
public class SubcategoiaController {
    @Autowired
    private SubcategoriaRep srp;
        @Autowired
    private CategoriaRep crp;
    @GetMapping("/subcategorias")
    public List<Subcategoria> getSubcategorias() {
        return srp.findAll();
    }
    @GetMapping("/subcategorias/{id}")
    public List<Subcategoria> getSubcategoriasViaCategoria(@PathVariable long id) {
        try{
            Categoria categoria = crp.findById(id);
            return srp.findByCategoria(categoria);
        }catch(Exception exception){
            exception.printStackTrace();
            return null;
        }
    }
    public List<Subcategoria> getSubcategoriaViaID(List<Long> id){
        return srp.findAllById(id);
    }
    @GetMapping("/subcategoriaExiste")
    public boolean existeEstaSub(@RequestParam long categoria_id,@RequestParam String nome) {
        
        return srp.existsByNomeAndCategoriaId(nome,categoria_id);
    }
    @GetMapping("/subcategoriasPosting")
    public long postageDeSubcategoria(@RequestParam String nome,@RequestParam long categoria_id) {
        try{

            Subcategoria salva = new Subcategoria();
            salva.setNome(nome);
            salva.setCategoria(crp.findById(categoria_id));
            salva = srp.save(salva);
            if(salva.getId()!=0){
                return salva.getId();
            }else{
                return 0;
            }
        }catch(Exception e){
            return 0;
        }
    }
}
