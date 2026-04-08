package com.cibertec.demo.controller;

import com.cibertec.demo.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controlador {

    @Autowired
    private CitaService citaService;

    @GetMapping("/inicio")
    public String inicio(Pageable pageable, Model model) {
        model.addAttribute("detalles", citaService.listarDetalles(pageable));
        return "inicio";
    }
}
