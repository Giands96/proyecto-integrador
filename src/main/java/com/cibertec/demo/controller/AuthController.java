package com.cibertec.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.demo.dto.AuthResponse;
import com.cibertec.demo.dto.LoginRequest;
import com.cibertec.demo.dto.registrorequest;
import com.cibertec.demo.modelo.Usuario;
import com.cibertec.demo.repository.UsuarioRepository;
import com.cibertec.demo.security.CustomUserDetailsService;
import com.cibertec.demo.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          CustomUserDetailsService customUserDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registrorequest request) {

        if (usuarioRepository.findByUsuario(request.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getUsuario());
        usuario.setClave(passwordEncoder.encode(request.getClave()));
        usuario.setRol(request.getRol() == null || request.getRol().isBlank() ? "CLIENTE" : request.getRol());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsuario(),
                    request.getClave()
                )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsuario());
            String token = jwtService.generateToken(userDetails);

            Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario()).orElseThrow();

            return ResponseEntity.ok(new AuthResponse(token, usuario.getUsuario(), usuario.getRol()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o clave incorrectos");
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> perfil() {
        return ResponseEntity.ok("Acceso permitido con JWT");
    }
}