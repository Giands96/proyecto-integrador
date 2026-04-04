package com.cibertec.demo.dto;

import com.cibertec.demo.modelo.Rol;

public class registrorequest {

    private String usuario;
    private String clave;
    private Rol rol;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}