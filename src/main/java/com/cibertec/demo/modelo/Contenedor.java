package com.cibertec.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Contenedor")
public class Contenedor {

    @Id
    private String numeroContenedor;

    private String tipo;
    private String numeroBL;

    // Getters y Setters
    public String getNumeroContenedor() { return numeroContenedor; }
    public void setNumeroContenedor(String numeroContenedor) { this.numeroContenedor = numeroContenedor; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNumeroBL() { return numeroBL; }
    public void setNumeroBL(String numeroBL) { this.numeroBL = numeroBL; }
}
