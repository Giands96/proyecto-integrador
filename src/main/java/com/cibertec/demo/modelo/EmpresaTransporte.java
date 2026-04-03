package com.cibertec.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "EmpresaTransporte")
public class EmpresaTransporte {

    @Id
    @Column(name = "RucEmpresa")
    private String rucEmpresa;

    @Column(name = "NombreEmpresa")
    private String nombreEmpresa;

    public EmpresaTransporte() {}

    public String getRucEmpresa() { return rucEmpresa; }
    public void setRucEmpresa(String rucEmpresa) { this.rucEmpresa = rucEmpresa; }

    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
}
