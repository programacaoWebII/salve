package com.example.demo.RestController;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Aluguel;
import com.example.demo.entities.Grupo;
import com.example.demo.entities.NormalUser;
import com.example.demo.entities.auxEntity.GrupoAux;
import com.example.demo.repositories.AluguelRep;
import com.example.demo.repositories.GrupoRep;
import com.example.demo.repositories.NormalUserRep;
import com.example.demo.repositories.SubcategoriaRep;



@RestController
public class RestFormControll {
    @Autowired
    private GrupoRep grp;
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
    @GetMapping("/formulario")
    public boolean salvaTudo(@RequestParam String string) {
        try {
            System.out.println(string);
            JSONObject jSONObject = new JSONObject(string);
            GrupoAux grupoAux = GrupoAux.ConstroiUmGrupoViaJSONobject(jSONObject);
            Grupo gp = grupoAux.getGrupo();
            gp.setSubcategorias(srp.findAllById(grupoAux.getSubcategorias()));
            grp.save(gp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
           return false;
        }
        
        return true;
    }
    
}
