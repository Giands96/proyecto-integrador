package com.cibertec.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Placa")
public class Placa {

    @Id
    private String numeroPlaca; // ← Debe llamarse igual que la columna

    public Placa() {}

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }
}

