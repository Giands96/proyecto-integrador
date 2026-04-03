package com.cibertec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.demo.modelo.Contenedor;

public interface ContenedorRepository extends JpaRepository<Contenedor, String> {

    List<Contenedor> findByNumeroBL(String numeroBL);

}

