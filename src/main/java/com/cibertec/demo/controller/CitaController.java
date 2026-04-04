package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.*;
import com.cibertec.demo.repository.*;
import com.cibertec.demo.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private ChoferRepository choferRepository;

    @Autowired
    private CamionRepository camionRepository;

    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.listarCitas();
    }

    @GetMapping("/detalles")
    public List<DetalleCita> listarDetalles() {
        return citaService.listarDetalles();
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
            
            if (request.getIdChofer() != null) {
                detalle.setChofer(choferRepository.findById(request.getIdChofer()).orElse(null));
            }

            if (request.getIdCamion() != null) {
                detalle.setCamion(camionRepository.findById(request.getIdCamion()).orElse(null));
            }

            detalle.setEstado(DetalleCita.EstadoDetalle.POR_ASIGNAR);
            detalle.setObservacion(request.getObservacion());

            citaService.guardarDetalle(detalle);

            return ResponseEntity.ok("Cita y detalle guardados correctamente con ID: " + cita.getIdCita());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }

    public static class CitaCompletaRequest {
        private Integer idCliente;
        private Integer idDestinatario;
        private Integer idTerminalOrigen;
        private Integer idTerminalDestino;
        private Integer idCarga;
        private Integer idChofer;
        private Integer idCamion;
        private String observacion;

        // Getters and Setters
        public Integer getIdCliente() { return idCliente; }
        public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
        public Integer getIdDestinatario() { return idDestinatario; }
        public void setIdDestinatario(Integer idDestinatario) { this.idDestinatario = idDestinatario; }
        public Integer getIdTerminalOrigen() { return idTerminalOrigen; }
        public void setIdTerminalOrigen(Integer idTerminalOrigen) { this.idTerminalOrigen = idTerminalOrigen; }
        public Integer getIdTerminalDestino() { return idTerminalDestino; }
        public void setIdTerminalDestino(Integer idTerminalDestino) { this.idTerminalDestino = idTerminalDestino; }
        public Integer getIdCarga() { return idCarga; }
        public void setIdCarga(Integer idCarga) { this.idCarga = idCarga; }
        public Integer getIdChofer() { return idChofer; }
        public void setIdChofer(Integer idChofer) { this.idChofer = idChofer; }
        public Integer getIdCamion() { return idCamion; }
        public void setIdCamion(Integer idCamion) { this.idCamion = idCamion; }
        public String getObservacion() { return observacion; }
        public void setObservacion(String observacion) { this.observacion = observacion; }
    }
}
