package com.cibertec.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Carreta")
public class Carreta {

    @Id
    private String numeroCarreta; // ← igual a la columna

    public Carreta() {}

    public String getNumeroCarreta() {
        return numeroCarreta;
    }

    public void setNumeroCarreta(String numeroCarreta) {
        this.numeroCarreta = numeroCarreta;
    }
}