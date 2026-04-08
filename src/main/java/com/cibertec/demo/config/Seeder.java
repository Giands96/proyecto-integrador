package com.cibertec.demo.config;

import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import com.cibertec.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class Seeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Seeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "ransa_admin@402";


        // Crear usuario admin si no existe
        if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {
            String rawPassword = generateRandomPassword(12);
            Usuario usuario = Usuario.builder()
                    .nombres("Admin")
                    .apellidos("User")
                    .username(adminUsername)
                    .password(passwordEncoder.encode(rawPassword))
                    .rol(Rol.ADMINISTRADOR)
                    .build();

            usuarioRepository.save(usuario);

            System.out.println("====================================");
            System.out.println("Usuario administrador creado:");
            System.out.println("username: " + adminUsername);
            System.out.println("password: " + rawPassword);
            System.out.println("====================================");
        }
    }

    private String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        // recortar a la longitud solicitada
        return encoded.length() > length ? encoded.substring(0, length) : encoded;
    }
}
