package com.cibertec.demo.dto;

import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class registrorequest {

    private String usuario;
    private String clave;
    private String nombres;
    private String apellidos;
    private Rol rol;


    // Método personalizado para facilitar el mapeo desde la entidad
    public static registrorequest fromEntity(String token, Usuario usuario) {
        return registrorequest.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .usuario(usuario.getUsername())
                .clave(usuario.getPassword())
                .rol(usuario.getRol())
                .build();
    }

}