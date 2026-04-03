package com.cibertec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.demo.modelo.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
	@Query("SELECT c FROM Cita c WHERE c.numeroContenedor LIKE %?1%")
    List<Cita> buscarPorContenedor(String numeroContenedor);
}



