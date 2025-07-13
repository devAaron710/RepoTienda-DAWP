package com.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller /*@ ANOTACIONES O DECORADORES*/
public class IndexController {
    
    @RequestMapping("/")//url al que responde
    public String index(Model model) {
        return "index";//nombre de la vista en templates a mostrar
    }
    
//    @RequestMapping("/informacion")
//    public String info() {
//        return "info";
//    }
    
}
