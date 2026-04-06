package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Chofer;
import com.cibertec.demo.repository.ChoferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/choferes")
public class ChoferController {

    @Autowired
    private ChoferRepository choferRepository;

    @GetMapping
    public List<Chofer> listar() {
        return choferRepository.findAll();
    }

    @PostMapping
    public Chofer crear(@RequestBody Chofer chofer) {
        return choferRepository.save(chofer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chofer> actualizar(@PathVariable Integer id, @RequestBody Chofer choferDetalles) {
        return choferRepository.findById(id)
                .map(chofer -> {
                    chofer.setNombresCompletos(choferDetalles.getNombresCompletos());
                    chofer.setLicencia(choferDetalles.getLicencia());
                    chofer.setDisponibilidad(choferDetalles.getDisponibilidad());
                    return ResponseEntity.ok(choferRepository.save(chofer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return choferRepository.findById(id)
                .map(chofer -> {
                    choferRepository.delete(chofer);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
