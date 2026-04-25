package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.DetalleCita;
import com.cibertec.demo.repository.DetalleCitaRepository;
import com.cibertec.demo.service.ChatbotCargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class PublicController {

    @Autowired
    private ChatbotCargaService chatbotCargaService;

    @GetMapping("/consulta-carga")
    public ResponseEntity<String> consultarCarga(
            @RequestParam String idSeguimiento,
            @RequestParam String documento,
            @RequestParam(defaultValue = "¿Me puedes dar detalles de mi carga?") String pregunta) {

        String respuestaIA = chatbotCargaService.consultarEstadoCargaBot(idSeguimiento, documento, pregunta);
        return ResponseEntity.ok(respuestaIA);
    }
}
