package com.cibertec.demo.service;

import com.cibertec.demo.modelo.*;
import com.cibertec.demo.repository.CitaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaService citaService;

    // PRUEBA UNITARIA 3
    @Test
    void liberarCamionYCancelarCargaAlCancelarCita() {
        //* ARRANGE
        Camion camionAsignado = new Camion();
        camionAsignado.setDisponibilidad(false); // Simula estar ocupado

        Carga cargaAsignada = new Carga();
        cargaAsignada.setEstado(CargaEstado.PENDIENTE);

        Cita citaSimulada = new Cita();
        citaSimulada.setIdCita(1L);
        citaSimulada.setCamion(camionAsignado);
        citaSimulada.setCarga(cargaAsignada);
        citaSimulada.setUsuario(new Usuario());

        when(citaRepository.findById(1L)).thenReturn(Optional.of(citaSimulada));
        when(citaRepository.save(any(Cita.class))).thenAnswer(i -> i.getArgument(0));

        //* ACT
        Cita resultado = citaService.actualizarEstado(1L, Cita.EstadoCita.CANCELADO);

        //* ASSERT
        assertEquals(Cita.EstadoCita.CANCELADO, resultado.getEstado());
        assertTrue(camionAsignado.isDisponibilidad(), "El camión debe liberarse al cancelar");
        assertNull(resultado.getUsuario(), "El chofer debe desasignarse");
        assertEquals(CargaEstado.CANCELADA, cargaAsignada.getEstado(), "La carga debe cancelarse");
        verify(citaRepository, times(1)).save(citaSimulada);
    }

    // PRUEBA UNITARIA 4
    @Test
    void entregarCitaActualizaCargaYLiberarCamion() {
        //* ARRANGE
        Camion camionRuta = new Camion();
        camionRuta.setDisponibilidad(false);

        Carga cargaTransito = new Carga();
        cargaTransito.setEstado(CargaEstado.EN_TRANSITO);

        Cita citaSimulada = new Cita();
        citaSimulada.setIdCita(2L);
        citaSimulada.setEstado(Cita.EstadoCita.EN_CAMINO);
        citaSimulada.setCamion(camionRuta);
        citaSimulada.setCarga(cargaTransito);

        when(citaRepository.findById(2L)).thenReturn(Optional.of(citaSimulada));
        when(citaRepository.save(any(Cita.class))).thenAnswer(i -> i.getArgument(0));

        //* ACT
        Cita resultado = citaService.actualizarEstado(2L, Cita.EstadoCita.ENTREGADO);

        //* ASSERT
        assertEquals(Cita.EstadoCita.ENTREGADO, resultado.getEstado());
        assertEquals(CargaEstado.ENTREGADA, cargaTransito.getEstado(), "Carga debe actualizarse a ENTREGADA");
        assertNotNull(resultado.getFechaLlegada(), "Debe registrarse la fecha y hora de llegada");
        assertTrue(camionRuta.isDisponibilidad(), "El camión debe quedar disponible");
    }
}