package com.cibertec.demo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Chofer")
public class Chofer {

    @Id
    @Column(name = "DniChofer")
    private String dniChofer;

    @Column(name = "NombreChofer")
    private String nombreChofer;

    public Chofer() {}

    public String getDniChofer() { return dniChofer; }
    public void setDniChofer(String dniChofer) { this.dniChofer = dniChofer; }

    public String getNombreChofer() { return nombreChofer; }
    public void setNombreChofer(String nombreChofer) { this.nombreChofer = nombreChofer; }
}


