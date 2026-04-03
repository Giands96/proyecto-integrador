package com.cibertec.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.demo.modelo.BL;

@Repository
public interface BLRepository extends JpaRepository<BL, String> {

    @Query("SELECT b FROM BL b WHERE b.numeroBL = :numeroBL AND b.rucCliente = :rucCliente")
    BL buscarBLPorNumeroYCliente(
            @Param("numeroBL") String numeroBL,
            @Param("rucCliente") String rucCliente
    );

}

