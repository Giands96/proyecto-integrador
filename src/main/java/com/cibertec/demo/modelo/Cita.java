package com.cibertec.demo.modelo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnoreProperties({"citas", "cargas", "hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_destinatario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Destinatario destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_terminal_origen", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Terminal terminalOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_terminal_destino")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Terminal terminalDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carga", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Carga carga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_camion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Camion camion;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "dias_estimados")
    private Integer diasEstimados;

    @Column(name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCita estado = EstadoCita.POR_ASIGNAR;

    @Column(name = "observacion", length = 255)
    private String observacion;

    public enum EstadoCita {
        POR_ASIGNAR, PROGRAMADO, EN_CAMINO, ENTREGADO, CANCELADO
    }
}