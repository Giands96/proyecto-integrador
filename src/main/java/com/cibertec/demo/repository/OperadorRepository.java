package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Integer> {
}
