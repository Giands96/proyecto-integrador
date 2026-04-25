package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Carga;
import com.cibertec.demo.modelo.CargaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargaRepository extends JpaRepository<Carga, Long> {
    List<Carga> findByEstado(CargaEstado estado);
    Optional<Carga> findByCodigoSeguimiento(String codigoSeguimiento);
}
