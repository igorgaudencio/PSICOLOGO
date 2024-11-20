package com.example.PSICOLOGO.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class principalControle {
    
    @GetMapping("/administrativo")
    public String acessarPrincipal(){
        return "administrativo/home";
    }

}
