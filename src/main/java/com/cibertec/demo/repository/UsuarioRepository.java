package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Rol;
import com.cibertec.demo.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findAll(Pageable pageable);
    Page<Usuario> findByRol(Rol rol, Pageable pageable);
    List<Usuario> findByRol(Rol rol);
    Optional<Usuario> findByUsername(String username);
}
