package com.cibertec.demo.exceptions;

public class UsetNotFoundException extends RuntimeException {
    public UsetNotFoundException(String message) {
        super("Usuario no encontrado: " + message);
    }
}
