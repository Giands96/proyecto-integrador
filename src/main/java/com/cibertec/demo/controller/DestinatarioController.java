package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Destinatario;
import com.cibertec.demo.repository.DestinatarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinatarios")
public class DestinatarioController {

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @GetMapping
    public Page<Destinatario> listar(@PageableDefault(size = 10) Pageable pageable) {
        return destinatarioRepository.findAll(pageable);
    }

    @PostMapping
    public Destinatario crear(@RequestBody Destinatario destinatario) {
        return destinatarioRepository.save(destinatario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destinatario> actualizar(@PathVariable Long id, @RequestBody Destinatario detalles) {
        return destinatarioRepository.findById(id)
                .map(dest -> {
                    dest.setTipoDocumento(detalles.getTipoDocumento());
                    dest.setNumeroDocumento(detalles.getNumeroDocumento());
                    dest.setNombreCompleto(detalles.getNombreCompleto());
                    dest.setTelefono(detalles.getTelefono());
                    dest.setDepartamento(detalles.getDepartamento());
                    dest.setProvincia(detalles.getProvincia());
                    dest.setDistrito(detalles.getDistrito());
                    dest.setDireccionEntrega(detalles.getDireccionEntrega());
                    return ResponseEntity.ok(destinatarioRepository.save(dest));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return destinatarioRepository.findById(id)
                .map(dest -> {
                    destinatarioRepository.delete(dest);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
