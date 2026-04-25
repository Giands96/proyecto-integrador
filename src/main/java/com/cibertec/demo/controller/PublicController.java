package com.cibertec.demo.controller;

import com.cibertec.demo.repository.CargaRepository;
import com.cibertec.demo.service.ChatbotCargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class PublicController {

    @Autowired
    private ChatbotCargaService chatbotCargaService;

    @Autowired
    private CargaRepository cargaRepository;

    @GetMapping("/consulta-carga/tracking")
    public ResponseEntity<?> consultarCargaPorTracking(
            @RequestParam String idSeguimiento,
            @RequestParam String documento
    ) {
        return cargaRepository
                .findByCodigoSeguimientoAndClienteNumeroDocumento(idSeguimiento, documento)
                .map(carga -> ResponseEntity.ok(Map.of(
                        "codigoSeguimiento", carga.getCodigoSeguimiento(),
                        "tipoCarga", carga.getTipoCarga(),
                        "descripcion", carga.getDescripcionCarga(),
                        "estado", carga.getEstado().name(),
                        "cliente", carga.getCliente().getNombresRazonSocial()
                )))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .body(Map.of(
                                "mensaje", "No se encontró una carga con ese código de seguimiento y documento."
                        )));
    }

    @GetMapping("/consulta-carga")
    public ResponseEntity<String> consultarCarga(
            @RequestParam String idSeguimiento,
            @RequestParam String documento,
            @RequestParam(defaultValue = "¿Me puedes dar detalles de mi carga?") String pregunta) {

        String respuestaIA = chatbotCargaService.consultarEstadoCargaBot(idSeguimiento, documento, pregunta);
        return ResponseEntity.ok(respuestaIA);
    }
}
