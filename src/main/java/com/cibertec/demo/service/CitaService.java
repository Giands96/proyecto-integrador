package com.cibertec.demo.service;

import com.cibertec.demo.dto.CitaCompletaRequest;
import com.cibertec.demo.modelo.*;
import com.cibertec.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CitaService {


    private final ClienteRepository clienteRepository;
    private final DestinatarioRepository destinatarioRepository;
    private final TerminalRepository terminalRepository;
    private final CargaRepository cargaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CamionRepository camionRepository;
    private final CitaRepository citaRepository;


    public Page<Cita> listarCitas(Pageable pageable) {
        return citaRepository.findAll(pageable);
    }

    @Transactional
    public Cita crearCitaCompleta(CitaCompletaRequest request) {

        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Destinatario destinatario = destinatarioRepository.findById(request.getIdDestinatario())
                .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        Terminal terminalOrigen = terminalRepository.findById(request.getIdTerminalOrigen())
                .orElseThrow(() -> new RuntimeException("Terminal origen no encontrada"));

        Carga carga = cargaRepository.findById(request.getIdCarga())
                .orElseThrow(() -> new RuntimeException("Carga no encontrada"));

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setDestinatario(destinatario);
        cita.setTerminalOrigen(terminalOrigen);
        cita.setCarga(carga);

        if (request.getIdTerminalDestino() != null) {
            Terminal terminalDestino = terminalRepository.findById(request.getIdTerminalDestino())
                    .orElseThrow(() -> new RuntimeException("Terminal destino no encontrada"));
            cita.setTerminalDestino(terminalDestino);
        }

        if (request.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (usuario.getRol() != Rol.CHOFER) {
                throw new RuntimeException("El usuario asignado no es un chofer");
            }

            cita.setUsuario(usuario);
        }

        if (request.getIdCamion() != null) {
            Camion camion = camionRepository.findById(request.getIdCamion())
                    .orElseThrow(() -> new RuntimeException("Camión no encontrado"));

            if (!camion.isDisponibilidad()) {
                throw new RuntimeException("El camión no está disponible");
            }

            cita.setCamion(camion);
            camion.setDisponibilidad(false);
        }

        if (request.getDiasEstimados() != null) {
            cita.setDiasEstimados(request.getDiasEstimados());
            cita.setFechaLlegada(LocalDateTime.now().plusDays(request.getDiasEstimados()));
        }

        cita.setEstado(Cita.EstadoCita.PROGRAMADO);
        cita.setObservacion(request.getObservacion());

        return citaRepository.save(cita);
    }

    public Cita actualizarEstado(Long id, Cita.EstadoCita nuevoEstado) {
        return citaRepository.findById(id)
                .map(c -> {
                    c.setEstado(nuevoEstado);
                    return citaRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }
}