package com.cibertec.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.demo.modelo.Placa;

public interface PlacaRepository extends JpaRepository<Placa, String> {
    boolean existsByNumeroPlacaIgnoreCase(String numeroPlaca);
}






