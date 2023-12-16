package com.salve.biblioteca.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
    
    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal OidcUser accessToken, Model model) {

        model.addAttribute("accessToken", accessToken);
        // Retorna o nome do template Handlebars (sem a extensão .hbs)
        return "mypage";
    }
    @GetMapping("/")
    public String redireciona() {
        return "redirect:"+myPage();
    }
    @GetMapping("/index")
    public String myPage() {
        return "index";
    }
}