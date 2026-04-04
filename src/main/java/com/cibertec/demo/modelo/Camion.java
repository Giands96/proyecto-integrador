package com.cibertec.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "camion")
@Data
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camion")
    private Integer idCamion;

    @Column(name = "placa", nullable = false, unique = true, length = 15)
    private String placa;

    @Column(name = "disponibilidad", nullable = false)
    private Integer disponibilidad = 1;

}
