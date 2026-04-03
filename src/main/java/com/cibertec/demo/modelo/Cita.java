package com.cibertec.demo.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCita")
    private Integer idCita;

    @Column(name = "NumeroCita")
    private String numeroCita;

    @ManyToOne
    @JoinColumn(name = "RucCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "RucAgencia")
    private AgenciaAduana agencia;

    @ManyToOne
    @JoinColumn(name = "RucEmpresa")
    private EmpresaTransporte empresa;

    @ManyToOne
    @JoinColumn(name = "DniChofer")
    private Chofer chofer;

    @Column(name = "NumeroPlaca")
    private String numeroPlaca;

    @Column(name = "NumeroCarreta")
    private String numeroCarreta;

    @Column(name = "NumeroBL")
    private String numeroBL;

    @Column(name = "NumeroContenedor")
    private String numeroContenedor;

    @Column(name = "Estado")
    private String estado;

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(String numeroCita) {
        this.numeroCita = numeroCita;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public AgenciaAduana getAgencia() {
        return agencia;
    }

    public void setAgencia(AgenciaAduana agencia) {
        this.agencia = agencia;
    }

    public EmpresaTransporte getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getNumeroCarreta() {
        return numeroCarreta;
    }

    public void setNumeroCarreta(String numeroCarreta) {
        this.numeroCarreta = numeroCarreta;
    }

    public String getNumeroBL() {
        return numeroBL;
    }

    public void setNumeroBL(String numeroBL) {
        this.numeroBL = numeroBL;
    }

    public String getNumeroContenedor() {
        return numeroContenedor;
    }

    public void setNumeroContenedor(String numeroContenedor) {
        this.numeroContenedor = numeroContenedor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @PrePersist
    public void generarDatosIniciales() {
        if (this.numeroCita == null || this.numeroCita.isEmpty()) {
            int aleatorio = 100000 + (int)(Math.random() * 900000);
            this.numeroCita = String.valueOf(aleatorio);
        }

        if (this.estado == null || this.estado.isEmpty()) {
            this.estado = "PENDIENTE";
        }
    }
}