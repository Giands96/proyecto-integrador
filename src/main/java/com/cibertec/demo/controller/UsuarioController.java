package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import com.cibertec.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public ResponseEntity<?> getEmpleados() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/operador")
    public ResponseEntity<?> getOperadores() {
        return ResponseEntity.ok(usuarioRepository.findByRol(Rol.OPERADOR));
    }

    @GetMapping("/chofer")
    public ResponseEntity<?> getChoferes() {
        return ResponseEntity.ok(usuarioRepository.findByRol(Rol.CHOFER));
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody Usuario datosActualizados) {
        return usuarioRepository.findById(id).map(usuario -> {
            // Actualizamos solo los datos permitidos
            usuario.setNombres(datosActualizados.getNombres());
            usuario.setApellidos(datosActualizados.getApellidos());
            usuario.setRol(datosActualizados.getRol());

            // Guardamos los cambios
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}


