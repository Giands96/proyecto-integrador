package com.cibertec.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "AgenciaAduana")
public class AgenciaAduana {

    @Id
    private String rucAgencia;
    private String nombreAgencia;

    public AgenciaAduana() {}

    public String getRucAgencia() { return rucAgencia; }
    public void setRucAgencia(String rucAgencia) { this.rucAgencia = rucAgencia; }

    public String getNombreAgencia() { return nombreAgencia; }
    public void setNombreAgencia(String nombreAgencia) { this.nombreAgencia = nombreAgencia; }
}

