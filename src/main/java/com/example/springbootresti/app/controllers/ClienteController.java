package com.example.springbootresti.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @GetMapping(path = {"/", ""})
    public String index(){
        return "redirect:/api/clientes";
    }
}
