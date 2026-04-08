package com.cibertec.demo.modelo;

import com.cibertec.demo.modelo.dto.ClienteCargaDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carga")
@Data
public class Carga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga")
    private Long idCarga;

    @Column(name = "tipo_carga", nullable = false, length = 50)
    private String tipoCarga;

    @Column(name = "descripcion_carga", nullable = false, columnDefinition = "TEXT")
    private String descripcionCarga;

    @Column(name = "codigo_seguimiento", unique = true, length = 20)
    private String codigoSeguimiento;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private CargaEstado estado = CargaEstado.PENDIENTE;

    @Column(name="cliente", nullable = false)
    private ClienteCargaDTO cliente;

}
