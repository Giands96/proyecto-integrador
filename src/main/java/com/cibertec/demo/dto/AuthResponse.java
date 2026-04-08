package com.cibertec.demo.dto;

import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String usuario;
    private Rol rol;

    // Método personalizado para facilitar el mapeo desde la entidad
    public static AuthResponse fromEntity(String token, Usuario usuario) {
        return AuthResponse.builder()
                .token(token)
                .usuario(usuario.getUsername())
                .rol(usuario.getRol())
                .build();
    }
}