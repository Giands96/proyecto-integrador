package com.cibertec.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carga")
@Data
public class Carga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carga")
    private Integer idCarga;

    @Column(name = "tipo_carga", nullable = false, length = 50)
    private String tipoCarga;

    @Column(name = "descripcion_carga", nullable = false, columnDefinition = "TEXT")
    private String descripcionCarga;

}
