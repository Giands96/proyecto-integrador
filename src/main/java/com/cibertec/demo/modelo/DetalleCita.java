package com.cibertec.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "detalle_cita")
@Data
public class DetalleCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_destinatario", nullable = false)
    private Destinatario destinatario;

    @ManyToOne
    @JoinColumn(name = "id_terminal_origen", nullable = false)
    private Terminal terminalOrigen;

    @ManyToOne
    @JoinColumn(name = "id_terminal_destino")
    private Terminal terminalDestino;

    @ManyToOne
    @JoinColumn(name = "id_carga", nullable = false)
    private Carga carga;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_camion")
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
