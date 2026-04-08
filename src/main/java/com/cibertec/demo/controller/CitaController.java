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

import java.time.LocalDateTime;
import java.util.List;
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

    @GetMapping("/detalles")
    public Page<DetalleCita> listarDetalles(@PageableDefault(size = 10) Pageable pageable) {
        return citaService.listarDetalles(pageable);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCitaCompleta(@RequestBody CitaCompletaRequest request) {
        try {
            // 1. Crear Cita
            Cita cita = new Cita();
            cita = citaService.guardarCita(cita);

            // 2. Crear Detalle
            DetalleCita detalle = new DetalleCita();
            detalle.setCita(cita);

            Cliente cliente = clienteRepository.findById(request.getIdCliente()).orElse(null);
            Destinatario dest = destinatarioRepository.findById(request.getIdDestinatario()).orElse(null);
            Terminal origen = terminalRepository.findById(request.getIdTerminalOrigen()).orElse(null);
            Carga carga = cargaRepository.findById(request.getIdCarga()).orElse(null);

            if (cliente == null || dest == null || origen == null || carga == null) {
                return ResponseEntity.badRequest().body("Datos de cliente, destinatario, terminal u origen no válidos");
            }

            detalle.setCliente(cliente);
            detalle.setDestinatario(dest);
            detalle.setTerminalOrigen(origen);
            detalle.setCarga(carga);

            if (request.getIdTerminalDestino() != null) {
                detalle.setTerminalDestino(terminalRepository.findById(request.getIdTerminalDestino()).orElse(null));
            }

            if (request.getIdUsuario() != null) {
                detalle.setUsuario(usuarioRepository.findById(request.getIdUsuario()).orElse(null));
            }

            if (request.getIdCamion() != null) {
                detalle.setCamion(camionRepository.findById(request.getIdCamion()).orElse(null));
            }

            // NUEVA LÓGICA DE TIEMPO ESTIMADO ⏱️
            if (request.getDiasEstimados() != null) {
                detalle.setDiasEstimados(request.getDiasEstimados());
                // Calculamos la fecha de llegada sumando los días a la fecha actual
                detalle.setFechaLlegada(LocalDateTime.now().plusDays(request.getDiasEstimados()));
            }

            detalle.setEstado(DetalleCita.EstadoDetalle.POR_ASIGNAR);
            detalle.setObservacion(request.getObservacion());

            citaService.guardarDetalle(detalle);

            return ResponseEntity.ok("Cita y detalle guardados correctamente con ID: " + cita.getIdCita());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }


    @PutMapping("/detalles/{id}/estado")
    public ResponseEntity<?> actualizarEstadoCita(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String estadoStr = request.get("estado");
            if (estadoStr == null) {
                return ResponseEntity.badRequest().body("El campo 'estado' es requerido");
            }

            DetalleCita.EstadoDetalle nuevoEstado = DetalleCita.EstadoDetalle.valueOf(estadoStr.toUpperCase());

            DetalleCita detalleActualizado = citaService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(detalleActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido. Use: POR_ASIGNAR, PROGRAMADO, EN_CAMINO, ENTREGADO, CANCELADO");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar el estado: " + e.getMessage());
        }
    }


}
