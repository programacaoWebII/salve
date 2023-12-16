package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.RestController.RestFormControll;
import com.example.demo.entities.Categoria;
import com.example.demo.entities.Grupo;
import com.example.demo.entities.NormalUser;
import com.example.demo.repositories.AdminRep;
import com.fasterxml.jackson.core.JsonProcessingException;


@Controller
public class HomeController {
    @Autowired
    RestFormControll controll;
    @Autowired
    AdminRep adrp;
    
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser bearer_token,Model model){
        return "redirect:"+inicio(bearer_token,model);
    }

    @GetMapping("visaodoproduto")
    public String inicio(@AuthenticationPrincipal OidcUser bearer_token,Model model) {

        if(controll.existeUsuario(bearer_token.getName())){
            model.addAttribute("user",controll.encontraPorId(bearer_token.getName()));
            List<Grupo> livros = controll.getLivros();
            List<Categoria> categorias = controll.getCategorias();
            model.addAttribute("livros",livros);
            model.addAttribute("categoria", categorias);
            
            return "visaodoproduto";
        }
        else{
            return "vocenaoexiste";
        }
    }
    @GetMapping("add")
    public String add(@AuthenticationPrincipal OidcUser bearer_token,Model model,boolean redirected) {
        if(redirected){
            return "adicionarlivros";
        }
        if(adrp.existsById(bearer_token.getName())){
            List<Categoria> lst = controll.getCategorias();
            model.addAttribute("categoria", lst);
            model.addAttribute("user",bearer_token);
            
            return "adicionarlivros";
        }else{
            model.addAttribute("user", bearer_token);
            return "redirect:tuto";
        }
        
    }
     @GetMapping("historico")
    public String historico(@AuthenticationPrincipal OidcUser bearer_token,Model model){
        if(controll.existeUsuario(bearer_token.getName())){
            model.addAttribute("user", controll.encontraPorId(bearer_token.getName()));
            model.addAttribute("categoria", controll.getCategorias());
            model.addAttribute("alugueis", controll.getAlugueisDoUsuario(bearer_token.getName()));
            return "historico";
        }else{
            return "vocenaoexiste";
        }
    }

    @GetMapping("tuto")
    public String getMethodName(@AuthenticationPrincipal OidcUser bearer_token,Model model) {
        model.addAttribute("repo",bearer_token);
        model.addAttribute("user", bearer_token);
        return "dados";
    }
    @GetMapping("vocenaoexiste")
    public String completaCadastro(Model model,@AuthenticationPrincipal OidcUser bearer_token) {
        if(controll.existeUsuario(bearer_token.getName())){
            return "redirect:"+inicio(bearer_token, model);
        }
        return "vocenaoexiste";
    }
    @GetMapping("/vocenaoexisteok")
    public String terminoCadastro(@AuthenticationPrincipal OidcUser bearer_token ,@RequestParam String cpf,@RequestParam String imagemLink ,Model model) throws JsonProcessingException {
        NormalUser entidadeUsuario = new NormalUser();
        entidadeUsuario.setCpf(cpf);
        entidadeUsuario.setImagemLink(imagemLink);
        entidadeUsuario.setName(bearer_token.getName());
        System.out.println("aqui");
        java.util.Map<String, Object> attributes = bearer_token.getAttributes();
        String email = (String) attributes.get("email");
        entidadeUsuario.setEmail(email);
        entidadeUsuario.setGivenName((String)attributes.get("given_name"));
        entidadeUsuario.setFamilyName((String) attributes.get("family_name"));
        controll.terminaCadastro(entidadeUsuario);
        return "redirect:"+inicio(bearer_token, model);
    }
}

