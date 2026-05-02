package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Carga;
import com.cibertec.demo.repository.CargaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CargaControllerTest {
    @Mock
    private CargaRepository cargaRepository;

    @InjectMocks
    private CargaController cargaController;

    @Test
    void generarCodigoSeguimientoAlCrearCarga() {
        //* ARRANGE - Configuramos el escenario de prueba
        Carga nuevaCarga = new Carga();
        nuevaCarga.setDescripcionCarga("Carga de frutas frescas");
        nuevaCarga.setTipoCarga("REFRIGERADA");
        when(cargaRepository.save(any(Carga.class))).thenAnswer(i -> i.getArgument(0));

        //* ACT - Ejecutamos la acción que queremos probar
        Carga cargaResultado = cargaController.crear(nuevaCarga);

        //* ASSERT - Verificamos que el resultado sea el esperado
        assertNotNull(cargaResultado.getCodigoSeguimiento(), "El controlador debió generar un código");
        assertTrue(cargaResultado.getCodigoSeguimiento().contains("-"), "El código debe tener formato de año");
        verify(cargaRepository, times(1)).save(nuevaCarga);
    }

    @Test
    void eliminarCargaExitosaRetornaHttp200() {
        //* ARRANGE
        Long idCarga = 10L;
        Carga cargaSimulada = new Carga();
        cargaSimulada.setIdCarga(idCarga);
        when(cargaRepository.findById(idCarga)).thenReturn(Optional.of(cargaSimulada));

        //* ACT
        ResponseEntity<Void> respuesta = cargaController.eliminar(idCarga);

        //* ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode(), "Debe retornar código HTTP 200");
        verify(cargaRepository, times(1)).delete(cargaSimulada);
    }
}
