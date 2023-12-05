package com.salve.biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salve.biblioteca.entities.Categoria;

@Controller
public class PageController {
    @GetMapping("/mypage")
    public String myPage(Model model) {
        Categoria a = new Categoria();
        a.setDescricao("rola de macaco");
        a.setId(69);
        a.setNome("rola torta");
        // Adicione atributos ao modelo conforme necessário
        model.addAttribute("a",a);
        // Retorna o nome do template Handlebars (sem a extensão .hbs)
        return "mypage";
    }
}