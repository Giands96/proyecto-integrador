package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.EmpresaTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpresaTransporteRepository extends JpaRepository<EmpresaTransporte, String> {
    List<EmpresaTransporte> findByNombreEmpresaContainingIgnoreCase(String nombreEmpresa);
}









