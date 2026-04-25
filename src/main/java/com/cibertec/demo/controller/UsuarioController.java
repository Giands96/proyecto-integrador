package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import com.cibertec.demo.repository.UsuarioRepository;
import com.cibertec.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<Page<Usuario>> getEmpleados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> empleados = usuarioService.listarEmpleados(pageable);

        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/operador")
    public ResponseEntity<?> getOperadores() {
        return ResponseEntity.ok(usuarioRepository.findByRol(Rol.OPERADOR));
    }

    @GetMapping("/chofer")
    public ResponseEntity<?> getChoferes() {
        return ResponseEntity.ok(usuarioRepository.findByRol(Rol.CHOFER));
    }

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


