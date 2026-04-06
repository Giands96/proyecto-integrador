package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Camion;
import com.cibertec.demo.repository.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    @Autowired
    private CamionRepository camionRepository;

    @GetMapping
    public List<Camion> listar() {
        return camionRepository.findAll();
    }

    @PostMapping
    public Camion crear(@RequestBody Camion camion) {
        return camionRepository.save(camion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Camion> actualizar(@PathVariable Integer id, @RequestBody Camion camionDetalles) {
        return camionRepository.findById(id)
                .map(camion -> {
                    camion.setPlaca(camionDetalles.getPlaca());
                    camion.setDisponibilidad(camionDetalles.getDisponibilidad());
                    return ResponseEntity.ok(camionRepository.save(camion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return camionRepository.findById(id)
                .map(camion -> {
                    camionRepository.delete(camion);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
