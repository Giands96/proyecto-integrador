package com.cibertec.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.demo.modelo.Carreta;

public interface CarretaRepository extends JpaRepository<Carreta, String> {
    boolean existsByNumeroCarretaIgnoreCase(String numeroCarreta);
}

