package com.cibertec.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "terminal")
@Data
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_terminal")
    private Long idTerminal;

    @Column(name = "nombre_ubicacion", nullable = false, length = 100)
    private String nombreUbicacion;

    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    @Column(name = "distrito", nullable = false, length = 100)
    private String distrito;

}
