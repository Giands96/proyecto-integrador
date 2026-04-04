package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.DetalleCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCitaRepository extends JpaRepository<DetalleCita, Integer> {
}
