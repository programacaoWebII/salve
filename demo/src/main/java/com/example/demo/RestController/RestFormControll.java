package com.example.demo.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Aluguel;
import com.example.demo.entities.Categoria;
import com.example.demo.entities.Grupo;
import com.example.demo.entities.NormalUser;
import com.example.demo.entities.Subcategoria;
import com.example.demo.repositories.AluguelRep;
import com.example.demo.repositories.CategoriaRep;
import com.example.demo.repositories.GrupoRep;
import com.example.demo.repositories.NormalUserRep;
import com.example.demo.repositories.SubcategoriaRep;


@RestController
public class RestFormControll {
    @Autowired
    private GrupoRep grp;
    @Autowired
    private CategoriaRep crp;
    @Autowired
    private SubcategoriaRep srp;
    @Autowired
    private AluguelRep arp;
    @Autowired
    private NormalUserRep nurep;
    @GetMapping("/livros")
    public List<Grupo> getLivros(){
        return grp.findAll();
    }
    @GetMapping("/livro/{id}")
    public Grupo getLivros(@PathVariable long id) {
        return grp.findById(id);
    }
    
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
    
    @GetMapping("/aluguel")
    public List<Aluguel> getAlugueis() {
        return arp.findAll();
    }
    @GetMapping("/aluguel/usuario/{id}")
    public List<Aluguel> getAlugueisDoUsuario(@PathVariable String id){
        return arp.findByUsuarioIdName(id);
    }
    @GetMapping("/usuario/{param}")
    public NormalUser getAlugadorByCpf(String param) {
        return nurep.findByCpf(param);
    }
    @GetMapping("/usuario/{name}")
    public boolean existeUsuario(String name){
        return nurep.existsById(name);
    }
    public NormalUser terminaCadastro(NormalUser user) {
        return nurep.save(user);
    }
    public NormalUser encontraPorId(String name){
        return nurep.findByName(name);
    }
}
