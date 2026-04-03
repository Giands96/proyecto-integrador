package com.cibertec.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.demo.modelo.AgenciaAduana;

public interface AgenciaAduanaRepository extends JpaRepository<AgenciaAduana, String> {

    @Query("SELECT a FROM AgenciaAduana a WHERE a.nombreAgencia LIKE %:nombre%")
    List<AgenciaAduana> buscarPorNombre(@Param("nombre") String nombre);

}
