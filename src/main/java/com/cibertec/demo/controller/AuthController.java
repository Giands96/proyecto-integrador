package com.cibertec.demo.controller;

import com.cibertec.demo.dto.LoginRequest;
import com.cibertec.demo.dto.registrorequest;
import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import com.cibertec.demo.repository.RolRepository;
import com.cibertec.demo.repository.UsuarioRepository;
import com.cibertec.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsuario();
        String password = request.getClave();


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Usuario user = usuarioRepository.findByUsername(username).orElseThrow();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRol().getNombreRol()))
        );

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", token, "role", user.getRol().getNombreRol()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registrorequest request) {
        String username = request.getUsuario();
        String password = request.getClave();
        String rolName = "USER";
        if (request.getRol() != null && request.getRol().getNombreRol() != null) {
            rolName = request.getRol().getNombreRol();
        }

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "username and password are required"));
        }

        if (usuarioRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "username already exists"));
        }

        final String roleToUse = rolName;
        Rol rol = rolRepository.findByNombreRol(roleToUse).orElseGet(() -> {
            Rol r = new Rol();
            r.setNombreRol(roleToUse);
            return rolRepository.save(r);
        });

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .rol(rol)
                .build();

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("message", "user registered"));
    }
}