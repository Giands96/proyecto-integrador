package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Carga;
import com.cibertec.demo.repository.CargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cargas")
public class CargaController {

    @Autowired
    private CargaRepository cargaRepository;

    @GetMapping
    public Page<Carga> listar(@PageableDefault(size = 10) Pageable pageable) {
        return cargaRepository.findAll(pageable);
    }

    @PostMapping
    public Carga crear(@RequestBody Carga carga) {
        if (carga.getCodigoSeguimiento() == null || carga.getCodigoSeguimiento().isEmpty()) {
            String randomCode = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            carga.setCodigoSeguimiento(Year.now().getValue() + "-" + randomCode);
        }
        return cargaRepository.save(carga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carga> actualizar(@PathVariable Long id, @RequestBody Carga cargaDetalles) {
        return cargaRepository.findById(id)
                .map(carga -> {
                    carga.setTipoCarga(cargaDetalles.getTipoCarga());
                    carga.setDescripcionCarga(cargaDetalles.getDescripcionCarga());
                    // No permitimos actualizar el código de seguimiento una vez creado
                    return ResponseEntity.ok(cargaRepository.save(carga));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return cargaRepository.findById(id)
                .map(carga -> {
                    cargaRepository.delete(carga);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
