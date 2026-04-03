package com.cibertec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.demo.modelo.Chofer;

public interface ChoferRepository extends JpaRepository<Chofer, String> {

    // Buscar por similar (para transporte o futuros buscadores)
    List<Chofer> findByNombreChoferContainingIgnoreCase(String NombreChofer);
}
