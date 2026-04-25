package com.cibertec.demo.controller;

import com.cibertec.demo.dto.CitaCompletaRequest;
import com.cibertec.demo.modelo.*;
import com.cibertec.demo.repository.*;
import com.cibertec.demo.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private CargaRepository cargaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CamionRepository camionRepository;

    @GetMapping
    public Page<Cita> listarCitas(@PageableDefault(size = 10) Pageable pageable) {
        return citaService.listarCitas(pageable);
    }


    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCita(@RequestBody CitaCompletaRequest request) {
        try {
            Cita cita = citaService.crearCitaCompleta(request);
            return ResponseEntity.ok("Cita guardada correctamente con ID: " + cita.getIdCita());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoCita(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        try {
            String estadoStr = request.get("estado");

            if (estadoStr == null || estadoStr.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'estado' es requerido");
            }

            Cita.EstadoCita nuevoEstado = Cita.EstadoCita.valueOf(estadoStr.toUpperCase());

            Cita citaActualizada = citaService.actualizarEstado(id, nuevoEstado);

            return ResponseEntity.ok(citaActualizada);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Estado no válido. Use: POR_ASIGNAR, PROGRAMADO, EN_CAMINO, ENTREGADO, CANCELADO");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
