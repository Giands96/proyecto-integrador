package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CamionRepository extends JpaRepository<Camion, Long> {
    List<Camion> findByDisponibilidadTrue();
}
