package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Terminal;
import com.cibertec.demo.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminales")
public class TerminalController {

    @Autowired
    private TerminalRepository terminalRepository;

    @GetMapping
    public List<Terminal> listar() {
        return terminalRepository.findAll();
    }

    @PostMapping
    public Terminal crear(@RequestBody Terminal terminal) {
        return terminalRepository.save(terminal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Terminal> actualizar(@PathVariable Integer id, @RequestBody Terminal terminalDetalles) {
        return terminalRepository.findById(id)
                .map(terminal -> {
                    terminal.setNombreUbicacion(terminalDetalles.getNombreUbicacion());
                    terminal.setDepartamento(terminalDetalles.getDepartamento());
                    terminal.setProvincia(terminalDetalles.getProvincia());
                    terminal.setDistrito(terminalDetalles.getDistrito());
                    return ResponseEntity.ok(terminalRepository.save(terminal));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return terminalRepository.findById(id)
                .map(terminal -> {
                    terminalRepository.delete(terminal);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
