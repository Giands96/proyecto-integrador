package com.cibertec.demo.controller;

import com.cibertec.demo.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controlador {

    @Autowired
    private CitaService citaService;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("detalles", citaService.listarDetalles());
        return "inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
