package com.cibertec.demo.dto;

import com.cibertec.demo.modelo.Rol;

public class AuthResponse {

    private String token;
    private String usuario;
    private Rol rol;

    public AuthResponse() {
    }

    public AuthResponse(String token, String usuario, Rol rol) {
        this.token = token;
        this.usuario = usuario;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}