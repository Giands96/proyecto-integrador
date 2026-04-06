package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.DetalleCita;
import com.cibertec.demo.repository.DetalleCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private DetalleCitaRepository detalleCitaRepository;

    @GetMapping("/tracking/{codigoSeguimiento}/{documento}")
    public ResponseEntity<?> consultarEstado(@PathVariable String codigoSeguimiento, @PathVariable String documento) {
        return detalleCitaRepository.findAll().stream()
                .filter(d -> d.getCarga() != null && 
                            codigoSeguimiento.equals(d.getCarga().getCodigoSeguimiento()) &&
                            (documento.equals(d.getCliente().getNumeroDocumento()) || 
                             documento.equals(d.getDestinatario().getNumeroDocumento())))
                .findFirst()
                .map(detalle -> ResponseEntity.ok(detalle))
                .orElse(ResponseEntity.notFound().build());
    }
}
