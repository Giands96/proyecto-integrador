package com.cibertec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.demo.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.nombreCliente LIKE %:nombre%")
    List<Cliente> buscarPorNombre(@Param("nombre") String nombre);

}
