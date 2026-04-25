package com.cibertec.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detalle_cita")
@Data
public class DetalleCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false)
    @JsonIgnoreProperties({"detalles","hibernateLazyInitializer", "handler"})
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_destinatario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Destinatario destinatario;

    @ManyToOne
    @JoinColumn(name = "id_terminal_origen", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Terminal terminalOrigen;

    @ManyToOne
    @JoinColumn(name = "id_terminal_destino")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Terminal terminalDestino;

    @ManyToOne
    @JoinColumn(name = "id_carga", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Carga carga;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_camion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Camion camion;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "dias_estimados")
    private Integer diasEstimados;

    @Column(name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDetalle estado;

    @Column(name = "observacion", length = 255)
    private String observacion;

    public enum EstadoDetalle {
        POR_ASIGNAR, PROGRAMADO, EN_CAMINO, ENTREGADO, CANCELADO
    }
}