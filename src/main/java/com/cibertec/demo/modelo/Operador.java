package com.cibertec.demo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "operador")
@Data
public class Operador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operador")
    private Long idOperador;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_terminal", nullable = false)
    private Terminal terminal;

    @Column(name = "nombres_completos", nullable = false, length = 150)
    private String nombresCompletos;

}
